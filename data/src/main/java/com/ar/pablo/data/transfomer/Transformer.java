package com.ar.pablo.data.transfomer;

import java.util.Collection;
import java.util.List;

/**
 * Interface that allows to perform transformation between objects
 */
public interface Transformer<T, Y> {

    /**
     * Transform a list of entity objects into a list of domain objects
     *
     * @param collection list of objects to be transformed
     * @return transformed list of transformed objects instances
     */
    List<T> transform(Collection<Y> collection);

    /**
     * Transform a entity object into a domain object
     *
     * @param object object to be transformed
     * @return transformed object of transformed object instance
     */
    T transform(Y object);
}
