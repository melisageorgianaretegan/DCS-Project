package Project5.priorityCars;

import Components.*;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataObjects.DataString;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.util.ArrayList;

public class ex2PriorityCars {

    public static void main(String[] args) {

        //--------------------------------------------------------------------
        //-------------------------------Lane1--------------------------------
        //--------------------------------------------------------------------

        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Main Petri";
        pn.NetworkPort = 1080;

        DataCar p_a = new DataCar();
        p_a.SetName("P_a");
        pn.PlaceList.add(p_a);

        DataCarQueue p_x1 = new DataCarQueue();
        p_x1.Value.Size = 3;
        p_x1.SetName("P_x1");
        pn.PlaceList.add(p_x1);

        DataCarQueue p_x2 = new DataCarQueue();
        p_x2.Value.Size = 3;
        p_x2.SetName("P_x2");
        pn.PlaceList.add(p_x2);

        DataCarQueue p_station = new DataCarQueue();
        p_station.Value.Size = 3;
        p_station.SetName("P_station");
        pn.PlaceList.add(p_station);

        DataString user_req = new DataString();
        user_req.SetName("User_Req");
        pn.PlaceList.add(user_req);

        DataCar p_t2 = new DataCar();
        p_t2.SetName("P_T2");
        pn.PlaceList.add(p_t2);

        DataCar p_t3 = new DataCar();
        p_t3.SetName("P_T3");
        pn.PlaceList.add(p_t3);

        DataString full = new DataString();
        full.SetName("full");
        full.SetValue("full");
        pn.ConstantPlaceList.add(full);

        DataString green= new DataString();
        green.SetName("green");
        green.SetValue("green");
        green.Printable= false;
        pn.ConstantPlaceList.add(green);

        // T_u1 ------------------------------------------------
        PetriTransition t_u1 = new PetriTransition(pn);
        t_u1.TransitionName = "T_u1";
        t_u1.InputPlaceName.add("P_a");

        Condition Tu1Ct11 = new Condition(t_u1, "P_a", TransitionCondition.NotNull);
        Condition Tu1Ct12 = new Condition(t_u1, "P_x1", TransitionCondition.CanAddCars);
        Condition Tu1Ct13 = new Condition(t_u1, "P_a", TransitionCondition.IsBus);
        Tu1Ct12.SetNextCondition(LogicConnector.AND, Tu1Ct13);
        Tu1Ct11.SetNextCondition(LogicConnector.AND, Tu1Ct12);

        GuardMapping grdTu11 = new GuardMapping();
        grdTu11.condition= Tu1Ct11;
        grdTu11.Activations.add(new Activation(t_u1, "P_a", TransitionOperation.AddElement, "P_x1"));
        t_u1.GuardMappingList.add(grdTu11);

        Condition T1Ct21 = new Condition(t_u1, "P_a", TransitionCondition.NotNull);
        Condition T1Ct22 = new Condition(t_u1, "P_x1", TransitionCondition.CanAddCars);
        Condition T1Ct23 = new Condition(t_u1, "P_a", TransitionCondition.IsPriorityCar);
        T1Ct22.SetNextCondition(LogicConnector.AND, T1Ct23);
        T1Ct21.SetNextCondition(LogicConnector.AND, T1Ct22);

        GuardMapping grdTu12 = new GuardMapping();
        grdTu12.condition= T1Ct21;
        grdTu12.Activations.add(new Activation(t_u1, "P_a", TransitionOperation.AddElement, "P_x1"));
        t_u1.GuardMappingList.add(grdTu12);

        t_u1.Delay = 0;
        pn.Transitions.add(t_u1);

        //TODO: Come back here
        //T_doesnotexist ------------------------------------------------
		/*PetriTransition t_e1 = new PetriTransition(pn);
		t_e1.TransitionName = "T_e1";
		t_e1.InputPlaceName.add("P_x1");

		Condition T2Ct1 = new Condition(t_e1, "P_TL1", TransitionCondition.Equal,"green");
		Condition T2Ct2 = new Condition(t_e1, "P_x1", TransitionCondition.HaveCar);
		T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

		GuardMapping grdT2 = new GuardMapping();
		grdT2.condition= T2Ct1;
		grdT2.Activations.add(new Activation(t_e1, "P_x1", TransitionOperation.PopElementWithoutTarget, "P_b1"));
		grdT2.Activations.add(new Activation(t_e1, "P_TL1", TransitionOperation.Move, "P_TL1"));

		t_e1.GuardMappingList.add(grdT2);

		t_e1.Delay = 1;
		pn.Transitions.add(t_e1);*/

        //T_s
        PetriTransition t_s = new PetriTransition(pn);
        t_s.TransitionName = "T_s";
        t_s.InputPlaceName.add("P_x1");

        Condition TsCt1 = new Condition(t_s, "P_x1", TransitionCondition.HaveTaxi);
        Condition TsCt2 = new Condition(t_s, "P_x1", TransitionCondition.HaveCarForMe);
        TsCt2.SetNextCondition(LogicConnector.AND, TsCt1);

        GuardMapping grdTs = new GuardMapping();
        grdTs.condition = TsCt1;
        grdTs.Activations.add(new Activation(t_s, "P_x1", TransitionOperation.PopElementWithTargetToQueue, "P_station"));

        t_s.GuardMappingList.add(grdTs);

        t_s.Delay = 1;
        pn.Transitions.add(t_s);

        //T_es
        PetriTransition t_es = new PetriTransition(pn);
        t_es.TransitionName = "T_es";
        t_es.InputPlaceName.add("P_station");
        t_es.InputPlaceName.add("User_req");

        Condition TesCt1 = new Condition(t_es, "P_station", TransitionCondition.HaveCar);
        Condition TesCt2 = new Condition(t_es, "User_req", TransitionCondition.NotNull);
        TesCt2.SetNextCondition(LogicConnector.AND, TesCt1);

        GuardMapping grdTes = new GuardMapping();
        grdTes.condition = TesCt1;

        ArrayList<String> input = new ArrayList<>();
        input.add(new String("P_station"));
        input.add(new String("User_req"));

        grdTes.Activations.add(new Activation(t_es, input, TransitionOperation.PopTaxiToQueue, "P_x2"));

        t_s.GuardMappingList.add(grdTes);

        t_s.Delay = 1;
        pn.Transitions.add(t_es);

        //T2
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T_2";
        t2.InputPlaceName.add("P_x2");

        Condition T2Ct1 = new Condition(t2, "P_x2", TransitionCondition.HaveCarForMe);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;
        grdT2.Activations.add(new Activation(t2, "P_x2", TransitionOperation.PopElementWithTarget, "P_T2"));

        t2.GuardMappingList.add(grdT2);

        t2.Delay = 1;
        pn.Transitions.add(t2);

        //T3
        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "T_3";
        t3.InputPlaceName.add("P_x2");

        Condition T3Ct1 = new Condition(t3, "P_x2", TransitionCondition.HaveCarForMe);

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = T3Ct1;
        grdT3.Activations.add(new Activation(t3, "P_x2", TransitionOperation.PopElementWithTarget, "P_T3"));

        t3.GuardMappingList.add(grdT3);

        t3.Delay = 1;
        pn.Transitions.add(t3);

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
