package controller;

/**
 * Interface that will be implemented by classes that use Visitor pattern.
 *
 * @author Camilo Jara Do Nascimento
 */
public interface LogicElement {
    /**
     * Accept method in visitor pattern.
     * @param visitor
     */
    void accept(Visitor visitor);
}
