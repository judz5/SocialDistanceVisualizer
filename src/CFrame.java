import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CFrame extends JPanel implements ActionListener {

    ArrayList<Person> people = new ArrayList<Person>();
    ArrayList<Point> point = new ArrayList<Point>();
    int time = 0;

    public static int percent;
    int population = 100;

    public static void main(String[] args) {
        CFrame c = new CFrame();
    }

    public CFrame() {
        JFrame f = new JFrame("Social Distancing Visualizer");
        f.setSize(800, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);

        JButton redo = new JButton("Restart");
        JSlider socialDist = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        JSlider pop = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel subPanel = new JPanel();

        subPanel.add(redo);
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
        subPanel.add(socialDist);
        socialDist.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                percent = socialDist.getValue();
            }
        });
        subPanel.add(pop);
        pop.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                population = pop.getValue();
            }
        });

        socialDist.setBorder(BorderFactory.createTitledBorder("Percent Social Distancing"));
        socialDist.setMajorTickSpacing(20);
        socialDist.setMinorTickSpacing(5);
        socialDist.setPaintLabels(true);
        socialDist.setPaintLabels(true);

        pop.setBorder(BorderFactory.createTitledBorder("Total Population"));
        pop.setMajorTickSpacing(100);
        pop.setMinorTickSpacing(5);
        pop.setPaintLabels(true);
        pop.setPaintLabels(true);

        panel.add(subPanel, BorderLayout.SOUTH);
        f.add(panel, BorderLayout.SOUTH);

        for(int i = 0; i<=population; i++){
            people.add(new Person()); // adds population (.08 are infected)
        }

        f.add(this);
        f.setVisible(true);

        Timer t = new Timer(16, this);
        t.restart();

    }

    public void paint(Graphics g){
        time+=16;

        point.add(new Point(time/16, Person.numInfected));

        super.paintComponent(g);

        for(Person p: people){
            p.paint(g);
        }

        for(int i = 0; i<people.size();i++){
            for(int k = i+1; k< people.size();k++){
                people.get(i).collision(people.get(k));
            }
        }

        g.setColor(Color.black);
        for(Point p: point){
            g.fillOval(p.time, 200-p.value, 5, 5);
        }

    }

    public void restart(){
        people.clear();
        for(int i = 0; i<population; i++){
            people.add(new Person()); // adds population (.08 are infected)
        }
        point.clear();
        time = 0;
        Person.resetNumInfected();
    }

    public static double getPercent(){
        return percent * .01;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
