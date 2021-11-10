package SpecialDataStructures;

import java.util.Vector;

public class Polynomial {

    private Vector<Integer> coefficients;
    private  Vector<Integer> powers;
    private int degree;


    public  Vector<Integer> getCoefficients() {
        return coefficients;
    }

    public  Vector<Integer> getPowers() {
        return powers;
    }

    public  void setPowers(Vector<Integer> Powers) {
         powers = Powers;
    }





    public void setCoefficients(Vector<Integer> coeficients) {
        this.coefficients = coeficients;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    /**
     * Constructor
     * @param coefficients - the list of coefficients
     * @param degree - the degree of the polynomial
     */
    public Polynomial(Vector<Integer> coefficients,Vector<Integer> powers, Integer degree)
    {
        setCoefficients(coefficients);
        setDegree(degree);
        this.powers = powers;
    }
    /**
     * Returns the result of evaluating this polynomial at the point x.
     * @param x the point at which to evaluate the polynomial
     * @return the integer whose value is {@code (this(x))}
     */
    public int evaluate(int x) {
        Integer sum =0 ;
        for (int i = 0; i < coefficients.size(); i++) {
            sum = sum + coefficients.get(i) * (int) Math.pow(x,powers.get(i));
        }
        //System.out.println(sum);
        return sum;

    }
}