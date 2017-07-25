package posti.examples.retail.cart.application.domain;

public interface SequenceProvider {
    long next(String seqName);
}
