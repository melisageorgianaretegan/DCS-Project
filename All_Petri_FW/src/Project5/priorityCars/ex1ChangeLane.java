package Project5.priorityCars;

import Components.*;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataObjects.DataString;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class ex1ChangeLane {

    public static void main(String[] args) {

        //--------------------------------------------------------------------
        //-------------------------------Lane1--------------------------------
        //--------------------------------------------------------------------

        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Main Petri";
        pn.NetworkPort = 1080;

        DataCar p1 = new DataCar();
        p1.SetName("P_a1");
        pn.PlaceList.add(p1);

        DataCarQueue p2 = new DataCarQueue();
        p2.Value.Size = 3;
        p2.SetName("P_x1");
        pn.PlaceList.add(p2);

        DataString p3 = new DataString();
        p3.SetName("P_TL1");
        pn.PlaceList.add(p3);

        DataCar p4 = new DataCar();
        p4.SetName("P_b1");
        pn.PlaceList.add(p4);

        DataString full = new DataString();
        full.SetName("full");
        full.SetValue("full");
        pn.ConstantPlaceList.add(full);


        DataString green= new DataString();
        green.SetName("green");
        green.SetValue("green");
        green.Printable= false;
        pn.ConstantPlaceList.add(green);

        // T1 ------------------------------------------------
        PetriTransition t_u1 = new PetriTransition(pn);
        t_u1.TransitionName = "T_u1";
        t_u1.InputPlaceName.add("P_a");

        Condition Tu1Ct11 = new Condition(t_u1, "P_a1", TransitionCondition.NotNull);
        Condition Tu1Ct12 = new Condition(t_u1, "P_x1", TransitionCondition.CanAddCars);
        Condition Tu1Ct13 = new Condition(t_u1, "P_a1", TransitionCondition.IsBus);
        Tu1Ct12.SetNextCondition(LogicConnector.AND, Tu1Ct13);
        Tu1Ct11.SetNextCondition(LogicConnector.AND, Tu1Ct12);

        GuardMapping grdTu11 = new GuardMapping();
        grdTu11.condition= Tu1Ct11;
        grdTu11.Activations.add(new Activation(t_u1, "P_a1", TransitionOperation.AddElement, "P_x1"));
        t_u1.GuardMappingList.add(grdTu11);

        Condition T1Ct21 = new Condition(t_u1, "P_a1", TransitionCondition.NotNull);
        Condition T1Ct22 = new Condition(t_u1, "P_x1", TransitionCondition.CanAddCars);
        Condition T1Ct23 = new Condition(t_u1, "P_a1", TransitionCondition.IsCar);
        T1Ct22.SetNextCondition(LogicConnector.AND, T1Ct23);
        T1Ct21.SetNextCondition(LogicConnector.AND, T1Ct22);

        GuardMapping grdTu12 = new GuardMapping();
        grdTu12.condition= T1Ct21;
        grdTu12.Activations.add(new Activation(t_u1, "P_a1", TransitionOperation.AddElement, "P_x1"));
        t_u1.GuardMappingList.add(grdTu12);

        t_u1.Delay = 0;
        pn.Transitions.add(t_u1);

        // T2 ------------------------------------------------
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T_e1";
        t2.InputPlaceName.add("P_x1");
        t2.InputPlaceName.add("P_TL1");


        Condition T2Ct1 = new Condition(t2, "P_TL1", TransitionCondition.Equal,"green");
        Condition T2Ct2 = new Condition(t2, "P_x1", TransitionCondition.HaveCar);
        T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition= T2Ct1;
        grdT2.Activations.add(new Activation(t2, "P_x1", TransitionOperation.PopElementWithoutTarget, "P_b1"));
        grdT2.Activations.add(new Activation(t2, "P_TL1", TransitionOperation.Move, "P_TL1"));

        t2.GuardMappingList.add(grdT2);

        Condition T2Ct21 = new Condition(t2, "P_x1", TransitionCondition.HavePriorityCar);

        GuardMapping grdT22 = new GuardMapping();
        grdT22.condition= T2Ct1;
        grdT22.Activations.add(new Activation(t2, "P_x1", TransitionOperation.PopElementWithoutTarget, "P_b1"));
        grdT22.Activations.add(new Activation(t2, "P_TL1", TransitionOperation.Move, "P_TL1"));

        t2.GuardMappingList.add(grdT22);

        t2.Delay = 1;
        pn.Transitions.add(t2);

        //-------------------------------------------------------------------------------------
        //----------------------------PN Start-------------------------------------------------
        //-------------------------------------------------------------------------------------

        System.out.println("Lane started \n ------------------------------");
        pn.Delay = 2000;
        //pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}
