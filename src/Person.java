import java.awt.*;

public class Person {

    // Location (x,y) *random
    int x, y;
    // Velocity (vx,vy) *movement
    int vx, vy;
    // Status (infected = 1, cured = 2, normal = 0)
    int status = 0;
    // Recovery Time
    int recoveryTime;

    static double percentDist;

    static int numInfected = 0;


    public Person(){

        x = (int)(Math.random()*790+0);
        y = (int)(Math.random()*590+0);

        if(Math.random()<.08){
            status = 1;
            numInfected+=1;// 8 percent of original population will be infected
        }

        percentDist = CFrame.getPercent();

        if(Math.random()>=percentDist){
            vx = (int)(Math.random()*(10+1)+-5);
            vy = (int)(Math.random()*(10+1)+-5);
        }

        recoveryTime = (int)(Math.random()*(7000-5000+1)+5000);

    }

    public void collision(Person p2){
        Rectangle per1 = new Rectangle(p2.x,p2.y, 10,10);
        Rectangle per2 = new Rectangle(this.x,this.y, 10,10);

        if(per1.intersects(per2)){
            if(this.status==1 && p2.status == 0){
                p2.status = 1;
                numInfected+=1;
            }else if(this.status==0 && p2.status==1){
                this.status = 1;
                numInfected+=1;
            }

        }

    }

    public void paint(Graphics g) {

        // First time using switch
        switch (status) {
            case 0 -> g.setColor(Color.gray); // normal
            case 1 -> g.setColor(Color.red); // infected
            case 2 -> g.setColor(Color.cyan); // cured
        }

        if(status == 1){
            recoveryTime-=16;
            if(recoveryTime<=0){
                status = 2;
                numInfected--;
            }
        }

        x+=vx;
        y+=vy;

        // Dont leave the frame *Checks if they go out of frame and flips their velocity
        if(x < 0 || x >= 790){
            vx *= -1;
        }
        if(y < 0 || y >= 590){
            vy *= -1;
        }

        g.fillOval(x, y, 10, 10);

    }

    public static void resetNumInfected(){
        numInfected = 0;
    }

}
