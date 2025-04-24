package org.mps.boundedqueue;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


public class ArrayBoundedQueueTest {
    
    // Test que comprueba que una cola recién creada está vacía
    @Test
    void nuevaColaDebeEstarVacia() {
        BoundedQueue<String> queue = new ArrayBoundedQueue<>(3);
        assertThat(queue.isEmpty()).isTrue();
        assertThat(queue.size()).isZero();
    }

    // Test que comprueba insertar un solo elemento
    @Test
    void insertarElementoIncrementaTamaño() {
        BoundedQueue<String> queue = new ArrayBoundedQueue<>(3);
        queue.put("Hola");
        assertThat(queue.isEmpty()).isFalse();
        assertThat(queue.size()).isEqualTo(1);
    }

    // Test FIFO: el primer elemento en entrar es el primero en salir
    @Test
    void debeSeguirOrdenFIFO() {
        BoundedQueue<String> queue = new ArrayBoundedQueue<>(3);
        queue.put("uno");
        queue.put("dos");
        String primero = queue.get();
        assertThat(primero).isEqualTo("uno");
        assertThat(queue.size()).isEqualTo(1);
    }

    // Test al llenar la cola completamente
    @Test
    void colaDebeIndicarLlena() {
        BoundedQueue<Integer> queue = new ArrayBoundedQueue<>(2);
        queue.put(1);
        queue.put(2);
        assertThat(queue.isFull()).isTrue();
    }

    // Test: insertar cuando la cola está llena debe lanzar excepción
    @Test
    void insertarEnColaLlenaLanzaExcepcion() {
        BoundedQueue<Integer> queue = new ArrayBoundedQueue<>(1);
        queue.put(1);
        assertThatThrownBy(() -> queue.put(2))
            .isInstanceOf(FullBoundedQueueException.class)
            .hasMessageContaining("llena");
    }

    // Test: sacar de una cola vacía debe lanzar excepción
    @Test
    void obtenerDeColaVaciaLanzaExcepcion() {
        BoundedQueue<String> queue = new ArrayBoundedQueue<>(2);
        assertThatThrownBy(queue::get)
            .isInstanceOf(EmptyBoundedQueueException.class)
            .hasMessageContaining("vacía");
    }

    // Test que verifica el comportamiento circular (reinicio de índices)
    @Test
    void comportamientoCircularFunciona() {
        BoundedQueue<Integer> queue = new ArrayBoundedQueue<>(2);
        queue.put(10); // [10]
        queue.put(20); // [10, 20]
        queue.get();   // se va 10
        queue.put(30); // [20, 30], debe colocar 30 en la posición 0
        assertThat(queue.get()).isEqualTo(20);
        assertThat(queue.get()).isEqualTo(30);
        assertThat(queue.isEmpty()).isTrue();
    }

    // Test: insertar null debe lanzar excepción
    @Test
    void insertarNullLanzaExcepcion() {
        BoundedQueue<String> queue = new ArrayBoundedQueue<>(2);
        assertThatThrownBy(() -> queue.put(null))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
}