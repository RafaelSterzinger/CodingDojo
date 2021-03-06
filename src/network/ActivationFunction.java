package network;

public enum ActivationFunction {

    //TODO Sprint 3, here you can implement different activation functions, like Sigmoid, Hyperbolic Tangent, ReLU etc.
    RELU(value -> Math.max(0, value), value -> value < 0 ? 0 : 1),
    IDENTITY(value -> value, value -> 1);

    private Function activationFunction;
    private Function derivative;

    ActivationFunction(Function activationFunction, Function derivationFunction) {
        this.activationFunction = activationFunction;
        this.derivative = derivationFunction;
    }

    public Function getDerivative() {
        return derivative;
    }

    public Function getActivationFunction() {
        return activationFunction;
    }

    @FunctionalInterface
    public interface Function {
        double activate(double value);
    }
}
