package src;
import grid.Counter;
import grid.CuttingResult;
import grid.Tilia;
import grid.TiliaCollection;
import grid.solvers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Test {

    // Uncomment the lines below to test your code for a few cases
    public static void main(String[] args){
        testCut();

        // The simple solver will not give the optimal solution
        //testSolver(new SimpleSolver(),15,correct15());

        //testSolver(new RecursiveSolver(),15,correct15());

        //testSolver(new DynamicSolver(15),15,correct15());
    }

    public static void testCut(){
        TiliaCollection collection = new TiliaCollection(200,42);

        HashMap<Integer, HashMap<Tilia,Integer>> correct = correctCut(collection);
        int count = 0;
        boolean isCorrectSolution = true;

        for (int j = 100 ; j <= 200; j+=10){
            for (int i = 10 ; i < 40; i+=5){
                Tilia test = collection.getGrid(j);
                ArrayList<Tilia> ok = test.cut(i);
                HashMap<Tilia,Integer> map = new HashMap<Tilia,Integer>();

                for (Tilia x: ok){
                    if (map.containsKey(x)){
                        map.put(x, map.get(x)+1);
                    }else{
                        map.put(x, 1);
                    }
                }
                //System.out.println("correct.put("+count+",new HashMap<Tilia, Integer>());");
                for (Tilia x: map.keySet()){
                    isCorrectSolution =  isCorrectSolution && (correct.get(count).get(x) == map.get(x));
                    //System.out.println("correct.get("+count+").put(collection.getGrid("+x.getSide()+"),"+map.get(x)+");");
                }
                count++;
            }
        }
        System.out.println("Correct cuts for these cases? "+isCorrectSolution);
    }

    public static void testSolver(Solver solver, int max, HashMap<Integer, HashMap<Integer,Integer>> correct){
        TiliaCollection collection = new TiliaCollection(max,42);
        boolean isCorrectSolution = true;

        for(int i = 1; i <= max; i++){
            Tilia test = collection.getGrid(i);
            CuttingResult solution = solver.getReward(test);
            HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
            for (int x: solution.getPieces()){
                if (map.containsKey(x)){
                    map.put(x, map.get(x)+1);
                }else{
                    map.put(x, 1);
                }
            }
            //System.out.println("correct.put("+i+",new HashMap<Integer, Integer>());");
            for (int x: map.keySet()){
                isCorrectSolution =  isCorrectSolution && (correct.get(i).get(x) == map.get(x));
                //System.out.println("correct.get("+i+").put("+x+","+map.get(x)+");");
            }
        }
        System.out.println("Correct solver for these cases? "+isCorrectSolution);
    }

    private static HashMap<Integer, HashMap<Tilia,Integer>> correctCut(TiliaCollection collection){
        HashMap<Integer, HashMap<Tilia,Integer>> correct = new HashMap<Integer, HashMap<Tilia,Integer>>();
        correct.put(0,new HashMap<Tilia, Integer>());
        correct.get(0).put(collection.getGrid(90),1);
        correct.get(0).put(collection.getGrid(10),19);
        correct.put(1,new HashMap<Tilia, Integer>());
        correct.get(1).put(collection.getGrid(5),4);
        correct.get(1).put(collection.getGrid(10),2);
        correct.get(1).put(collection.getGrid(85),1);
        correct.get(1).put(collection.getGrid(15),11);
        correct.put(2,new HashMap<Tilia, Integer>());
        correct.get(2).put(collection.getGrid(20),9);
        correct.get(2).put(collection.getGrid(80),1);
        correct.put(3,new HashMap<Tilia, Integer>());
        correct.get(3).put(collection.getGrid(75),1);
        correct.get(3).put(collection.getGrid(25),7);
        correct.put(4,new HashMap<Tilia, Integer>());
        correct.get(4).put(collection.getGrid(30),5);
        correct.get(4).put(collection.getGrid(10),6);
        correct.get(4).put(collection.getGrid(70),1);
        correct.put(5,new HashMap<Tilia, Integer>());
        correct.get(5).put(collection.getGrid(30),2);
        correct.get(5).put(collection.getGrid(5),12);
        correct.get(5).put(collection.getGrid(35),3);
        correct.get(5).put(collection.getGrid(65),1);
        correct.put(6,new HashMap<Tilia, Integer>());
        correct.get(6).put(collection.getGrid(10),21);
        correct.get(6).put(collection.getGrid(100),1);
        correct.put(7,new HashMap<Tilia, Integer>());
        correct.get(7).put(collection.getGrid(5),6);
        correct.get(7).put(collection.getGrid(95),1);
        correct.get(7).put(collection.getGrid(15),13);
        correct.put(8,new HashMap<Tilia, Integer>());
        correct.get(8).put(collection.getGrid(90),1);
        correct.get(8).put(collection.getGrid(20),9);
        correct.get(8).put(collection.getGrid(10),4);
        correct.put(9,new HashMap<Tilia, Integer>());
        correct.get(9).put(collection.getGrid(5),4);
        correct.get(9).put(collection.getGrid(10),4);
        correct.get(9).put(collection.getGrid(25),7);
        correct.get(9).put(collection.getGrid(85),1);
        correct.put(10,new HashMap<Tilia, Integer>());
        correct.get(10).put(collection.getGrid(30),5);
        correct.get(10).put(collection.getGrid(20),2);
        correct.get(10).put(collection.getGrid(80),1);
        correct.get(10).put(collection.getGrid(10),4);
        correct.put(11,new HashMap<Tilia, Integer>());
        correct.get(11).put(collection.getGrid(75),1);
        correct.get(11).put(collection.getGrid(5),14);
        correct.get(11).put(collection.getGrid(35),5);
        correct.put(12,new HashMap<Tilia, Integer>());
        correct.get(12).put(collection.getGrid(10),23);
        correct.get(12).put(collection.getGrid(110),1);
        correct.put(13,new HashMap<Tilia, Integer>());
        correct.get(13).put(collection.getGrid(105),1);
        correct.get(13).put(collection.getGrid(15),15);
        correct.put(14,new HashMap<Tilia, Integer>());
        correct.get(14).put(collection.getGrid(20),11);
        correct.get(14).put(collection.getGrid(100),1);
        correct.put(15,new HashMap<Tilia, Integer>());
        correct.get(15).put(collection.getGrid(20),2);
        correct.get(15).put(collection.getGrid(5),8);
        correct.get(15).put(collection.getGrid(25),7);
        correct.get(15).put(collection.getGrid(95),1);
        correct.put(16,new HashMap<Tilia, Integer>());
        correct.get(16).put(collection.getGrid(90),1);
        correct.get(16).put(collection.getGrid(30),7);
        correct.put(17,new HashMap<Tilia, Integer>());
        correct.get(17).put(collection.getGrid(5),6);
        correct.get(17).put(collection.getGrid(35),5);
        correct.get(17).put(collection.getGrid(85),1);
        correct.get(17).put(collection.getGrid(15),4);
        correct.put(18,new HashMap<Tilia, Integer>());
        correct.get(18).put(collection.getGrid(120),1);
        correct.get(18).put(collection.getGrid(10),25);
        correct.put(19,new HashMap<Tilia, Integer>());
        correct.get(19).put(collection.getGrid(5),4);
        correct.get(19).put(collection.getGrid(10),2);
        correct.get(19).put(collection.getGrid(115),1);
        correct.get(19).put(collection.getGrid(15),15);
        correct.put(20,new HashMap<Tilia, Integer>());
        correct.get(20).put(collection.getGrid(20),11);
        correct.get(20).put(collection.getGrid(10),4);
        correct.get(20).put(collection.getGrid(110),1);
        correct.put(21,new HashMap<Tilia, Integer>());
        correct.get(21).put(collection.getGrid(5),10);
        correct.get(21).put(collection.getGrid(105),1);
        correct.get(21).put(collection.getGrid(25),9);
        correct.put(22,new HashMap<Tilia, Integer>());
        correct.get(22).put(collection.getGrid(30),7);
        correct.get(22).put(collection.getGrid(10),6);
        correct.get(22).put(collection.getGrid(100),1);
        correct.put(23,new HashMap<Tilia, Integer>());
        correct.get(23).put(collection.getGrid(5),4);
        correct.get(23).put(collection.getGrid(10),4);
        correct.get(23).put(collection.getGrid(35),5);
        correct.get(23).put(collection.getGrid(25),2);
        correct.get(23).put(collection.getGrid(95),1);
        correct.put(24,new HashMap<Tilia, Integer>());
        correct.get(24).put(collection.getGrid(130),1);
        correct.get(24).put(collection.getGrid(10),27);
        correct.put(25,new HashMap<Tilia, Integer>());
        correct.get(25).put(collection.getGrid(5),6);
        correct.get(25).put(collection.getGrid(125),1);
        correct.get(25).put(collection.getGrid(15),17);
        correct.put(26,new HashMap<Tilia, Integer>());
        correct.get(26).put(collection.getGrid(20),13);
        correct.get(26).put(collection.getGrid(120),1);
        correct.put(27,new HashMap<Tilia, Integer>());
        correct.get(27).put(collection.getGrid(5),4);
        correct.get(27).put(collection.getGrid(10),2);
        correct.get(27).put(collection.getGrid(25),9);
        correct.get(27).put(collection.getGrid(115),1);
        correct.get(27).put(collection.getGrid(15),2);
        correct.put(28,new HashMap<Tilia, Integer>());
        correct.get(28).put(collection.getGrid(30),7);
        correct.get(28).put(collection.getGrid(20),2);
        correct.get(28).put(collection.getGrid(10),4);
        correct.get(28).put(collection.getGrid(110),1);
        correct.put(29,new HashMap<Tilia, Integer>());
        correct.get(29).put(collection.getGrid(105),1);
        correct.get(29).put(collection.getGrid(35),7);
        correct.put(30,new HashMap<Tilia, Integer>());
        correct.get(30).put(collection.getGrid(140),1);
        correct.get(30).put(collection.getGrid(10),29);
        correct.put(31,new HashMap<Tilia, Integer>());
        correct.get(31).put(collection.getGrid(135),1);
        correct.get(31).put(collection.getGrid(15),19);
        correct.put(32,new HashMap<Tilia, Integer>());
        correct.get(32).put(collection.getGrid(20),13);
        correct.get(32).put(collection.getGrid(130),1);
        correct.get(32).put(collection.getGrid(10),4);
        correct.put(33,new HashMap<Tilia, Integer>());
        correct.get(33).put(collection.getGrid(125),1);
        correct.get(33).put(collection.getGrid(25),11);
        correct.put(34,new HashMap<Tilia, Integer>());
        correct.get(34).put(collection.getGrid(30),9);
        correct.get(34).put(collection.getGrid(120),1);
        correct.put(35,new HashMap<Tilia, Integer>());
        correct.get(35).put(collection.getGrid(5),4);
        correct.get(35).put(collection.getGrid(10),6);
        correct.get(35).put(collection.getGrid(35),7);
        correct.get(35).put(collection.getGrid(115),1);
        correct.put(36,new HashMap<Tilia, Integer>());
        correct.get(36).put(collection.getGrid(150),1);
        correct.get(36).put(collection.getGrid(10),31);
        correct.put(37,new HashMap<Tilia, Integer>());
        correct.get(37).put(collection.getGrid(5),4);
        correct.get(37).put(collection.getGrid(10),2);
        correct.get(37).put(collection.getGrid(145),1);
        correct.get(37).put(collection.getGrid(15),19);
        correct.put(38,new HashMap<Tilia, Integer>());
        correct.get(38).put(collection.getGrid(20),15);
        correct.get(38).put(collection.getGrid(140),1);
        correct.put(39,new HashMap<Tilia, Integer>());
        correct.get(39).put(collection.getGrid(5),4);
        correct.get(39).put(collection.getGrid(10),4);
        correct.get(39).put(collection.getGrid(25),11);
        correct.get(39).put(collection.getGrid(135),1);
        correct.put(40,new HashMap<Tilia, Integer>());
        correct.get(40).put(collection.getGrid(30),9);
        correct.get(40).put(collection.getGrid(130),1);
        correct.get(40).put(collection.getGrid(10),6);
        correct.put(41,new HashMap<Tilia, Integer>());
        correct.get(41).put(collection.getGrid(20),2);
        correct.get(41).put(collection.getGrid(5),6);
        correct.get(41).put(collection.getGrid(35),7);
        correct.get(41).put(collection.getGrid(125),1);
        correct.get(41).put(collection.getGrid(15),2);
        correct.put(42,new HashMap<Tilia, Integer>());
        correct.get(42).put(collection.getGrid(160),1);
        correct.get(42).put(collection.getGrid(10),33);
        correct.put(43,new HashMap<Tilia, Integer>());
        correct.get(43).put(collection.getGrid(155),1);
        correct.get(43).put(collection.getGrid(5),6);
        correct.get(43).put(collection.getGrid(15),21);
        correct.put(44,new HashMap<Tilia, Integer>());
        correct.get(44).put(collection.getGrid(150),1);
        correct.get(44).put(collection.getGrid(20),15);
        correct.get(44).put(collection.getGrid(10),4);
        correct.put(45,new HashMap<Tilia, Integer>());
        correct.get(45).put(collection.getGrid(20),2);
        correct.get(45).put(collection.getGrid(5),8);
        correct.get(45).put(collection.getGrid(25),11);
        correct.get(45).put(collection.getGrid(145),1);
        correct.put(46,new HashMap<Tilia, Integer>());
        correct.get(46).put(collection.getGrid(30),9);
        correct.get(46).put(collection.getGrid(20),2);
        correct.get(46).put(collection.getGrid(10),4);
        correct.get(46).put(collection.getGrid(140),1);
        correct.put(47,new HashMap<Tilia, Integer>());
        correct.get(47).put(collection.getGrid(30),2);
        correct.get(47).put(collection.getGrid(5),12);
        correct.get(47).put(collection.getGrid(35),7);
        correct.get(47).put(collection.getGrid(135),1);
        correct.put(48,new HashMap<Tilia, Integer>());
        correct.get(48).put(collection.getGrid(10),35);
        correct.get(48).put(collection.getGrid(170),1);
        correct.put(49,new HashMap<Tilia, Integer>());
        correct.get(49).put(collection.getGrid(165),1);
        correct.get(49).put(collection.getGrid(15),23);
        correct.put(50,new HashMap<Tilia, Integer>());
        correct.get(50).put(collection.getGrid(160),1);
        correct.get(50).put(collection.getGrid(20),17);
        correct.put(51,new HashMap<Tilia, Integer>());
        correct.get(51).put(collection.getGrid(155),1);
        correct.get(51).put(collection.getGrid(5),10);
        correct.get(51).put(collection.getGrid(25),13);
        correct.put(52,new HashMap<Tilia, Integer>());
        correct.get(52).put(collection.getGrid(30),11);
        correct.get(52).put(collection.getGrid(150),1);
        correct.put(53,new HashMap<Tilia, Integer>());
        correct.get(53).put(collection.getGrid(5),14);
        correct.get(53).put(collection.getGrid(35),9);
        correct.get(53).put(collection.getGrid(145),1);
        correct.put(54,new HashMap<Tilia, Integer>());
        correct.get(54).put(collection.getGrid(10),37);
        correct.get(54).put(collection.getGrid(180),1);
        correct.put(55,new HashMap<Tilia, Integer>());
        correct.get(55).put(collection.getGrid(5),4);
        correct.get(55).put(collection.getGrid(10),2);
        correct.get(55).put(collection.getGrid(175),1);
        correct.get(55).put(collection.getGrid(15),23);
        correct.put(56,new HashMap<Tilia, Integer>());
        correct.get(56).put(collection.getGrid(20),17);
        correct.get(56).put(collection.getGrid(10),4);
        correct.get(56).put(collection.getGrid(170),1);
        correct.put(57,new HashMap<Tilia, Integer>());
        correct.get(57).put(collection.getGrid(5),4);
        correct.get(57).put(collection.getGrid(165),1);
        correct.get(57).put(collection.getGrid(10),2);
        correct.get(57).put(collection.getGrid(25),13);
        correct.get(57).put(collection.getGrid(15),2);
        correct.put(58,new HashMap<Tilia, Integer>());
        correct.get(58).put(collection.getGrid(30),11);
        correct.get(58).put(collection.getGrid(160),1);
        correct.get(58).put(collection.getGrid(10),6);
        correct.put(59,new HashMap<Tilia, Integer>());
        correct.get(59).put(collection.getGrid(155),1);
        correct.get(59).put(collection.getGrid(5),6);
        correct.get(59).put(collection.getGrid(35),9);
        correct.get(59).put(collection.getGrid(15),4);
        correct.put(60,new HashMap<Tilia, Integer>());
        correct.get(60).put(collection.getGrid(10),39);
        correct.get(60).put(collection.getGrid(190),1);
        correct.put(61,new HashMap<Tilia, Integer>());
        correct.get(61).put(collection.getGrid(5),6);
        correct.get(61).put(collection.getGrid(185),1);
        correct.get(61).put(collection.getGrid(15),25);
        correct.put(62,new HashMap<Tilia, Integer>());
        correct.get(62).put(collection.getGrid(20),19);
        correct.get(62).put(collection.getGrid(180),1);
        correct.put(63,new HashMap<Tilia, Integer>());
        correct.get(63).put(collection.getGrid(25),15);
        correct.get(63).put(collection.getGrid(175),1);
        correct.put(64,new HashMap<Tilia, Integer>());
        correct.get(64).put(collection.getGrid(30),11);
        correct.get(64).put(collection.getGrid(20),2);
        correct.get(64).put(collection.getGrid(10),4);
        correct.get(64).put(collection.getGrid(170),1);
        correct.put(65,new HashMap<Tilia, Integer>());
        correct.get(65).put(collection.getGrid(5),4);
        correct.get(65).put(collection.getGrid(165),1);
        correct.get(65).put(collection.getGrid(10),4);
        correct.get(65).put(collection.getGrid(35),9);
        correct.get(65).put(collection.getGrid(25),2);
        return correct;
    }

    private static HashMap<Integer, HashMap<Integer,Integer>> correct15(){
        TiliaCollection collection = new TiliaCollection(15,42);
        HashMap<Integer, HashMap<Integer,Integer>> correct = new HashMap<Integer, HashMap<Integer,Integer>>();
        correct.put(1,new HashMap<Integer, Integer>());
        correct.get(1).put(1,1);
        correct.put(2,new HashMap<Integer, Integer>());
        correct.get(2).put(2,1);
        correct.put(3,new HashMap<Integer, Integer>());
        correct.get(3).put(3,1);
        correct.put(4,new HashMap<Integer, Integer>());
        correct.get(4).put(1,7);
        correct.get(4).put(3,1);
        correct.put(5,new HashMap<Integer, Integer>());
        correct.get(5).put(1,4);
        correct.get(5).put(2,3);
        correct.get(5).put(3,1);
        correct.put(6,new HashMap<Integer, Integer>());
        correct.get(6).put(3,4);
        correct.put(7,new HashMap<Integer, Integer>());
        correct.get(7).put(1,13);
        correct.get(7).put(3,4);
        correct.put(8,new HashMap<Integer, Integer>());
        correct.get(8).put(2,7);
        correct.get(8).put(3,4);
        correct.put(9,new HashMap<Integer, Integer>());
        correct.get(9).put(3,9);
        correct.put(10,new HashMap<Integer, Integer>());
        correct.get(10).put(1,19);
        correct.get(10).put(3,9);
        correct.put(11,new HashMap<Integer, Integer>());
        correct.get(11).put(1,4);
        correct.get(11).put(2,9);
        correct.get(11).put(3,9);
        correct.put(12,new HashMap<Integer, Integer>());
        correct.get(12).put(3,16);
        correct.put(13,new HashMap<Integer, Integer>());
        correct.get(13).put(1,25);
        correct.get(13).put(3,16);
        correct.put(14,new HashMap<Integer, Integer>());
        correct.get(14).put(2,13);
        correct.get(14).put(3,16);
        correct.put(15,new HashMap<Integer, Integer>());
        correct.get(15).put(3,25);
        return correct;
    }

}