import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CFrame extends JPanel implements ActionListener {

    ArrayList<Person> people = new ArrayList<Person>();
    ArrayList<Point> point = new ArrayList<Point>();
    int time = 0;
    JCheckBox dist = new JCheckBox("Social Distance");

    public static void main(String[] args) {
        CFrame c = new CFrame();
    }

    public CFrame() {
        JFrame f = new JFrame("Social Distancing Visualizer");
        f.setSize(800, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);



        for(int i = 0; i<100; i++){
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

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
