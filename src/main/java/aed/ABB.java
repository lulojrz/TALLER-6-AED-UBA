package aed;

import java.util.NoSuchElementException;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> {
    // Agregar atributos privados del Conjunto
    private Nodo raiz;
    private int cardinal;


    private boolean pertenece;
    private  int compareTo;
   

    private class Nodo {
        // Agregar atributos privados del Nodo
        T valor;
        Nodo padre;
        Nodo izq;
        Nodo der;

        // Crear Constructor del nodo
        Nodo(T v) {
            valor = v;
            padre = null;
            der = null;
            izq = null;
        }
    }

    public ABB() {
        raiz = null;
        cardinal = 0;
    }

    public int cardinal() {
        return cardinal;
    }

    public T minimo() {
        Nodo minimo = null;
        Nodo actual = raiz;
        while (actual != null) {
            minimo = actual;
            actual = actual.izq;
        }
        return minimo.valor;
    }

    public Nodo minimoNodo(Nodo n) {
        if (n == null)
            return null;
        Nodo actual = n;
        while (actual.izq != null) {
            actual = actual.izq;
        }
        return actual;
    }

    public T maximo() {
        Nodo maximo = null;
        Nodo actual = raiz;
        while (actual != null) {
            maximo = actual;
            actual = actual.der;
        }
        return maximo.valor;
    }

    public void insertar(T elem) {
        if (raiz==null) {
            raiz = new Nodo(elem);
            cardinal =  1;
            return;
        }
        Nodo actual = raiz;
        Nodo padre = null;
        while (actual != null) {
            padre = actual;
            compareTo = elem.compareTo(actual.valor);
            if (compareTo == 0) {
                return;
            } else if (compareTo > 0) {
                actual = actual.der;
            } else {
                actual = actual.izq;
            }

        }

        Nodo nuevo = new Nodo(elem);
        nuevo.padre = padre;
        if (compareTo < 0) {
            padre.izq = nuevo;
        } else {
            padre.der = nuevo;
        }
        cardinal = cardinal + 1;

    }

    public boolean pertenece(T elem) {
        Nodo actual = raiz;
        while (actual != null) {
            compareTo = elem.compareTo(actual.valor);
            if (compareTo == 0) {
                return true;
            } else if (compareTo < 0) {
                actual = actual.izq;
            } else {
                actual = actual.der;
            }
        }
        return false;
    }

    public void eliminar(T elem) {
        if (raiz == null) {
            return;
        }
        Nodo actual = raiz;
        Nodo padre = null;
        while (actual != null && elem.compareTo(actual.valor) != 0) {
            padre = actual;
            int compareTo = elem.compareTo(actual.valor);
            if (compareTo < 0) {
                actual = actual.izq;
            } else {
                actual = actual.der;
            }

        }
        if (actual == null) {
            return;
        }

        if (actual.der != null && actual.izq != null) {
            Nodo sucesorPadre = actual;
            Nodo sucesor = actual.der;
            while (sucesor.izq != null) {
                sucesorPadre = sucesor;
                sucesor = sucesor.izq;
            }
            actual.valor = sucesor.valor;
            actual = sucesor;
            padre = sucesorPadre;
        }

        Nodo hijo = (actual.izq != null) ? actual.izq : actual.der;
        if (actual == raiz) {
            raiz = hijo;
            if (hijo!=null){
               hijo.padre = null;
            }
            

        } else if (padre.izq == actual) {
            padre.izq = hijo;
            if (hijo!=null){
               hijo.padre = padre;
            }
        } else {
            padre.der = hijo;
             if (hijo != null) {
            hijo.padre = padre;
        }
        }
      

        cardinal--;

    }

    public String toString() {
        String cadena = "{";
        ABB_Iterador it = this.iterador();
        while (it.haySiguiente()) {

            cadena += it.siguiente();
            if (it.haySiguiente()) {
                cadena += ",";
            }

        }
        cadena += "}";
        return cadena;
    }

    private Nodo sucesor(Nodo n) {
        Nodo actual = n;
        if (n == null)
            return null;

        if (n.der != null) {
            return minimoNodo(n.der);
        }

        Nodo p = n.padre;
        while (p != null && actual == p.der) {
            actual = p;
            p = p.padre;
        }
        return p;
    }

    public class ABB_Iterador implements Iterador<T> {
        private Nodo _actual = minimoNodo(raiz);

        public boolean haySiguiente() {
            return _actual != null;
        }

        public T siguiente() {
            if (!haySiguiente() || _actual == null) {
                throw new NoSuchElementException("No hay más elementos en el iterador");
            }
            T valor = _actual.valor;
            _actual = sucesor(_actual);
            return valor;
        }
    }

    public ABB_Iterador iterador() {
        return new ABB_Iterador();
    }

}
