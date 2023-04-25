package pl.symentis.alge.sprites;

import java.util.Collection;
import java.util.Iterator;

public class CyclicIterator<E, C extends Collection<E>> implements Iterator<E> {
    private final C mElements;
    private Iterator<E> mIterator;

    public CyclicIterator(C elements) {
        mElements = elements;
        mIterator = elements.iterator();
    }

    @Override
    public boolean hasNext() {
        if (!mIterator.hasNext()) {
            mIterator = mElements.iterator();
        }
        return mIterator.hasNext();
    }

    @Override
    public E next() {
        if (!mIterator.hasNext()) {
            mIterator = mElements.iterator();
        }
        return mIterator.next();
    }
}
