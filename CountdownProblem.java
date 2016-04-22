package countdownproblem;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class CountdownProblem extends Applet
        implements ActionListener {

    private static final long serialVersionUID = 1L;

    TextField box0, box1, box2, box3, box4, box5, targetbox, messagebox;
    int i = 0;
    int j = 0;
    int target = 0;
    int[] possib = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100};
    int[] select = {0, 0, 0, 0, 0, 0};
    int[] tools = {0, 0, 0, 0, 0, 0};
    int[] fun = {0, 0, 0, 0, 0, 0};
    int[] higher = {0, 0, 0, 0, 0, 0};
    int[] lower = {0, 0, 0, 0, 0, 0};
    int[] k = {0, 0, 0, 0, 0, 0};
    int[] sorted = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    String[] formula = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    String[] symbol = {"+", "-", "*", "/"};
    int bestsofar = 0;
    int level = 0;
    int thiscalc = 0;
    int done = 0;
    int domore = 0;
    String newform = "";
    String bestform = "";

    @Override
    public void init() {
        Button generate = new Button("Generate");
        generate.addActionListener(this);
        add(generate);
        box0 = new TextField(4);
        box1 = new TextField(4);
        box2 = new TextField(4);
        box3 = new TextField(4);
        box4 = new TextField(4);
        box5 = new TextField(4);
        add(box0);
        add(box1);
        add(box2);
        add(box3);
        add(box4);
        add(box5);
        targetbox = new TextField(8);
        add(targetbox);
        Button calculate = new Button("Calculate");
        calculate.addActionListener(this);
        add(calculate);
        messagebox = new TextField(60);
        add(messagebox);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String arg = event.getActionCommand();
        if ("Generate".equals(arg)) {
            for (i = 0; i < 6; i++) {
                select[i] = possib[i];
            }
            for (i = 6; i < 19; i++) {
                if (Math.random() * (i + 1) < 6) {
                    select[(int) (Math.random() * 6.0)] = possib[i];
                }
            }
            box0.setText("" + select[0]);
            box1.setText("" + select[1]);
            box2.setText("" + select[2]);
            box3.setText("" + select[3]);
            box4.setText("" + select[4]);
            box5.setText("" + select[5]);
            targetbox.setText("" + (int) ((Math.random() * 900) + 100));
            messagebox.setText("");

        }
        if ("Calculate".equals(arg)) {
            tools[0] = Integer.parseInt(box0.getText().trim());
            tools[1] = Integer.parseInt(box1.getText().trim());
            tools[2] = Integer.parseInt(box2.getText().trim());
            tools[3] = Integer.parseInt(box3.getText().trim());
            tools[4] = Integer.parseInt(box4.getText().trim());
            tools[5] = Integer.parseInt(box5.getText().trim());
            target = Integer.parseInt(targetbox.getText().trim());

            for (i = 0; i < 6; i++) {
                k[i] = 0;
                for (j = 0; j < 6; j++) {
                    if ((tools[i]) < tools[j] || (tools[i] == tools[j] && i < j)) {
                        k[i]++;
                    }
                }
                sorted[k[i]] = tools[i];
                formula[k[i]] = "" + sorted[k[i]];
            }

            level = 0;
            bestsofar = 0;
            higher[level] = ((42 - ((6 - level) * (7 - level))) / 2);
            lower[level] = higher[level] + 1;
            fun[level] = 0;
            domore = 0;

            while (domore < 2) {
                if (5 <= higher[0]) {
                    domore = 2;
                } else {
                    domore = 1;

                    if (((42 - ((6 - (level + 1)) * (7 - (level + 1)))) / 2) <= lower[level]) {
                        higher[level]++;
                        lower[level] = higher[level] + 1;
                        fun[level] = 0;
                        domore = 0;
                    }
                    if ((((42 - ((6 - (level + 1)) * (7 - (level + 1)))) / 2) <= (higher[level] + 1))
                            || (4 < level)) {
                        if (5 <= higher[0]) {
                            domore = 2;
                        } else {
                            level--;
                            fun[level]++;
                            domore = 0;
                        }
                    }
                }
                if (domore == 1) {
                    if ((fun[level] == 1 && sorted[higher[level]] <= sorted[lower[level]])
                            || (fun[level] == 2 && sorted[lower[level]] <= 1)
                            || (fun[level] == 3 && (0 < sorted[higher[level]] % sorted[lower[level]]
                            || sorted[lower[level]] <= 1))) {
                        fun[level]++;
                        domore = 0;
                    }
                    if (4 <= fun[level]) {
                        lower[level]++;
                        fun[level] = 0;
                        domore = 0;
                    }
                }

                if (domore == 1) {
                    newform = "(" + formula[higher[level]] + symbol[fun[level]];
                    newform = newform + formula[lower[level]] + ")";
                    if (fun[level] == 0) {
                        thiscalc = sorted[higher[level]] + sorted[lower[level]];
                    }
                    if (fun[level] == 1) {
                        thiscalc = sorted[higher[level]] - sorted[lower[level]];
                    }
                    if (fun[level] == 2) {
                        thiscalc = sorted[higher[level]] * sorted[lower[level]];
                    }
                    if (fun[level] == 3) {
                        thiscalc = sorted[higher[level]] / sorted[lower[level]];
                    }

                    if (((target - thiscalc < target - bestsofar)
                            && (bestsofar - target < target - thiscalc))
                            || ((target - bestsofar < target - thiscalc)
                            && (target - thiscalc < bestsofar - target))) {
                        bestsofar = thiscalc;
                        bestform = newform;
                        domore = 1;
                        if (bestsofar == target) {
                            messagebox.setText("DONE IT!    " + target + " = " + bestform);
                            domore = 2;
                        } else {
                            messagebox.setText("Best so far: " + thiscalc + " = " + newform);
                        }
                    }
                    if (domore < 2) {
                        i = ((42 - ((6 - (level)) * (7 - (level)))) / 2);
                        done = 0;
                        for (j = ((42 - ((6 - (level + 1)) * (7 - (level + 1)))) / 2);
                                j < ((42 - ((6 - (level + 2)) * (7 - (level + 2)))) / 2); j++) {
                            if (i == higher[level]) {
                                i++;
                            }
                            if (i == lower[level]) {
                                i++;
                            }
                            if (done == 0 && (sorted[i] <= thiscalc
                                    || ((42 - ((6 - (level + 1)) * (7 - (level + 1)))) / 2) <= i)) {
                                sorted[j] = thiscalc;
                                formula[j] = newform;
                                done = 1;
                            } else {
                                sorted[j] = sorted[i];
                                formula[j] = formula[i];
                                i++;
                            }
                        }
                        level++;
                        fun[level] = 0;
                        higher[level] = ((42 - ((6 - (level)) * (7 - (level)))) / 2);
                        lower[level] = higher[level] + 1;
                        domore = 0;
                    }
                }
            }
            if (5 <= higher[0]) {
                messagebox.setText("TRIED EVERYTHING  Best: " + bestsofar + " = " + bestform);
            }
        }
    }
}
