package Lab3;

import Components.*;
import DataObjects.DataFuzzy;
import DataOnly.FLRS;
import DataOnly.FV;
import DataOnly.Fuzzy;
import DataOnly.PlaceNameWithWeight;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.util.ArrayList;

public class ex2lab {
    public static void main(String[] args) {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "FlrsAdder/Subtractor";
        pn.NetworkPort = 1081;

        // -------------------------------------- ex1 ------------------------------------------------
        // For x1 = 0.2, w1 = 1 and For x2 = 0.3, w2 = 2  ==>  x3 = 0.76 AND x4 = -0.4 // TESTED
        // ------------------------------------- end -------------------------------------------------

        FLRS flrsAdderSubtractor = new FLRS(new FV(FZ.NL, FZ.ZR), new FV(FZ.NL, FZ.NM), new FV(FZ.NL, FZ.NL), new FV(FZ.NM, FZ.NL),new FV(FZ.ZR, FZ.NL),
                                            new FV(FZ.NL, FZ.PM), new FV(FZ.NL, FZ.ZR), new FV(FZ.NM, FZ.NM), new FV(FZ.ZR, FZ.NL),new FV(FZ.PM, FZ.NL),
                                            new FV(FZ.NL, FZ.PL), new FV(FZ.NM, FZ.PM), new FV(FZ.ZR, FZ.ZR), new FV(FZ.PM, FZ.NM),new FV(FZ.PL, FZ.NL),
                                            new FV(FZ.NM, FZ.PL), new FV(FZ.ZR, FZ.PL), new FV(FZ.PM, FZ.PM), new FV(FZ.PL, FZ.ZR),new FV(FZ.PL, FZ.NM),
                                            new FV(FZ.ZR, FZ.PL), new FV(FZ.PM, FZ.PL), new FV(FZ.PL, FZ.PL), new FV(FZ.PL, FZ.PM),new FV(FZ.PL, FZ.ZR));

        flrsAdderSubtractor.Print();

        DataFuzzy p1 = new DataFuzzy();
        p1.SetName("P1");
        p1.SetValue(new Fuzzy(0.2F));
        pn.PlaceList.add(p1);

        DataFuzzy p2 = new DataFuzzy();
        p2.SetName("P2");
        p2.SetValue(new Fuzzy(0.3F));
        pn.PlaceList.add(p2);

        DataFuzzy p3 = new DataFuzzy();
        p3.SetName("P3");
        pn.PlaceList.add(p3);

        DataFuzzy p4 = new DataFuzzy();
        p4.SetName("P4");
        pn.PlaceList.add(p4);

        // T1 ------------------------------------------------
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "T1";
        t1.InputPlaceName.add("P1");
        t1.InputPlaceName.add("P2");

        Condition T1Ct1 = new Condition(t1, "P1", TransitionCondition.NotNull);
        Condition T1Ct2 = new Condition(t1, "P2", TransitionCondition.NotNull);
        T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;

        ArrayList<PlaceNameWithWeight> input = new ArrayList<>();
        input.add(new PlaceNameWithWeight("P1", 1F));
        input.add(new PlaceNameWithWeight("P2", 2F));

        ArrayList<String> twoOutput = new ArrayList<>();
        twoOutput.add("P3");
        twoOutput.add("P4");

        grdT1.Activations.add(new Activation(t1, flrsAdderSubtractor, input, TransitionOperation.FLRS, twoOutput));

        t1.GuardMappingList.add(grdT1);

        t1.Delay = 0;
        pn.Transitions.add(t1);

        // -------------------------------------------

        System.out.println("Adder/Subtractor started \n ------------------------------");
        pn.Delay = 3000;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);

    }
}
