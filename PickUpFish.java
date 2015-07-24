package scripts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.*;

class ButtonFrame extends JFrame {

  ArrayList<Long> presses;
  JButton bChange; // reference to the button object
  JButton finish;

  // constructor for ButtonFrame
  ButtonFrame(String title) {
    super(title); // invoke the JFrame constructor
    presses = new ArrayList<Long>();

    setLayout(new FlowLayout()); // set the layout manager

    bChange = new JButton("Click Me!"); // construct a JButton
    finish = new JButton("Done");
    add(bChange); // add the button to the JFrame
    add(finish);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    bChange.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        presses.add(System.currentTimeMillis());
      }
    });

    finish.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        presses.stream().forEach(s -> writeToFile(s));
      }

      private void writeToFile(Long s) {
        try (PrintWriter out = new PrintWriter(
            new BufferedWriter(new FileWriter("dat.txt", true)))) {
          out.println(String.valueOf(s));
        } catch (IOException e) {
          // exception handling left as an exercise for the reader
        }
      }
    });
  }
}

public class PickUpFish {
  public static void main(String[] args) {
    ButtonFrame frm = new ButtonFrame("Button Demo");

    frm.setSize(300, 300);
    frm.setVisible(true);
  }
}