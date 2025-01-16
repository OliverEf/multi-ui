package edu.bothell.multi_ui.core;
import java.util.ArrayList;


public class Game {
    private final int                  MAX_PLAYERS = 3;
    private final ArrayList<Player>    p;
    private final State                s;
    private int                        turn;
    private Player                     active;

    public Game(Control c){
        this.turn = 0;
        this.s = new World();
        this.p = new ArrayList<>();
    }
    
    public Player addPlayer(Player p){
        this.p.add(p);
        if(this.active == null) active = p;

        return p;
    }

    public Player addPlayer(char c, String sId){
        Player p = new Player(c);
        p.setSId(sId);
        return addPlayer(p);
    }

    public char[] getPlayersChar(){
        char[] pcs = new char[p.size()];
        for(int i = 0; i < pcs.length; i++) 
            pcs[i] = p.get(i).getChar();
        
        return pcs;
    }
    
    public boolean isValid(int[] pos, String sId){
        System.out.println("isVAlid?"+s.getIt(pos)+"|" + sId+"|" + active.getSId()+"|");

        if(pos[0] > 2 || pos[1] > 2) return false;

        return s.isOpen(pos) && active.getSId().equals(sId);
    }
    public boolean checkGameOver(){

        
        // check draws
        if(turn > 8) return true;
        
        // check rows
        for(int y = 0; y < 3; y++){
            if(s.getIt(0, y) == s.getIt(1, y) && s.getIt(1, y) == s.getIt(2, y)) return true;
        }
        // check columns
        for(int x = 0; x < 3; x++){
            if(s.getIt(x, 0) == s.getIt(x, 1) && s.getIt(x, 1) == s.getIt(x, 2)) return true;
        }
        // check diagonals
        if(s.getIt(0, 0) == s.getIt(1, 1) && s.getIt(1, 1) == s.getIt(2, 2)) return true;
        // check diagonals other way
        if(s.getIt(0, 2) == s.getIt(1, 1) && s.getIt(1, 1) == s.getIt(2, 0)) return true;

        
        
        return false;
    }

    public char play(int[] pos, String sId){
        if(!isValid(pos, sId)) return ' ';
        turn++;
        this.s.setIt(active.getChar(), pos[0], pos[1]);

        if(checkGameOver()) return active.getChar();


        
        this.active = p.get( turn % p.size() );

        return active.getChar();
    }



    public Player getActive() {
        return this.active;
    }

    public State getState() {
        return this.s;
    }

    public Location getLocation(int x, int y) {
        return ((World)s).getLocation(x, y);
    }

    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    public int getPlayerCount() {
        return p.size();
    }

    public ArrayList<Player> getPlayers(){
        return this.p;
    }

    public int getTurn(){
        return this.turn;
    }


}
