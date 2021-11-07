package SetupStage;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.math.BigInteger;



public class SystemSetup {
    /**
     * Getter for G1
     * @return Field
     */
    public Field getG1() {
        return G1;
    }

    /**
     * Getter for G2
     * @return Field
     */
    public Field getG2() {
        return G2;
    }

    /**
     * Getter for GT
     * @return Field
     */
    public Field getGT() {
        return GT;
    }

    /**
     * Getter for Zr
     * @return Zr
     */
    public Field getZr() {
        return Zr;
    }

    /**
     * Getter for p-order of G1,G2,GT
     * @return BigInteger
     */
    public BigInteger getPrime_p() {
        return prime_p;
    }

    /**
     * Getter for g-generator of groups
     * @return Element
     */
    public Element getGenerator_g() {
        return generator_g;
    }
    /**
     * Getter for function-paring (e:G1xG2->GT)
     * @return Pairing
     */
    public Pairing getPairing() {
        return pairing;
    }

    private Field G1;
    private Field G2;
    private Field GT;
    private Field Zr;
    private BigInteger prime_p;
    private Element generator_g;
    private Pairing pairing;

    public SystemSetup()
    {
        PairingFactory pairingFactory = PairingFactory.getInstance();
        //pairingFactory.setUsePBCWhenPossible(true);
        this.pairing= pairingFactory.getPairing("params.properties");
        this.Zr = pairing.getZr();
        this.G1 = pairing.getG1();
        this.G2 = pairing.getG2();
        this.GT = pairing.getGT();
        this.prime_p = GT.getOrder();
        this.generator_g = G1.newRandomElement();

    }
}
