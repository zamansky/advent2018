import java.io.*;
import java.util.*;
public class Driver{

    public static void main(String[] args) {
	Dll l = new Dll();
	int i, lastscore=7095000, numplayers=431, player=0;
	long[] players = new long[numplayers];
        for (i=3;i<=lastscore;i++){
	    if (i%23!=0){
		l.inc();
		l.insert(i);
	    } else {
		players[player]+=i;
		for (int j = 0; j < 7; j++) {
		    l.dec();
		}
		players[player]+=l.getCurrent();
                l.delete();
	    }
	    player = (player+1)%numplayers;
	}
	Arrays.sort(players);
	System.out.println(players[numplayers-1]);
    }
}
