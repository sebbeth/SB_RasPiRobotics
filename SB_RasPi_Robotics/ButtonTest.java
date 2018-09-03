package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.ObservableButton;

import java.util.Observable;
import java.util.Observer;

public class ButtonTest implements Observer {

    public static void main(String[] args) {
        ButtonTest bt = new ButtonTest();
        System.out.println("TEST");

        ObservableButton button = new ObservableButton();
        bt.observe(button);
        while (true) {
            bt.update(button,null);
        }

    }

    public void observe(Observable o) {
        o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        boolean buttonPressed = ((ObservableButton) o).getState();
        System.out.println(buttonPressed);

    }
}
