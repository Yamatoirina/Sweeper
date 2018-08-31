package sweeper;

import java.awt.*;

class Flag {

    private Matrix flagMap;

     private int totalFlagged;
    private int totalClosed;



    void start(){
        flagMap = new Matrix(Box.CLOSED);
        totalFlagged = 0;
        totalClosed = Ranges.getSquare();

    }

    Box get (Coord coord){
        return flagMap.get (coord);
    }

      void setOpenedToBox(Coord coord) {
        flagMap.set (coord, Box.OPENED);
        totalClosed--;
     }

      private void setFlagedToBox(Coord coord) {
        flagMap.set (coord, Box.FLAGED);
        totalFlagged++;
     }


     private void setClosedToBox(Coord coord) {
         flagMap.set (coord, Box.CLOSED);
         totalFlagged--;
     }

     void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)){
            case FLAGED: setClosedToBox (coord);
            break;
            case CLOSED: setFlagedToBox(coord);
                break;
        }
     }


      int getTotalFlagged() {
         return totalFlagged;
     }

      int getTotalClosed() {
         return totalClosed;
     }

      void setFlaggedToLastClosedBoxes() {
        for (Coord coord : Ranges.getAllCoords())
            if (Box.CLOSED == flagMap.get(coord))
                setFlagedToBox(coord);
     }

     void setBombedToBox(Coord coord) {

        flagMap.set(coord, Box.BOMBED);
    }

     void setOpenedToClosedBox(Coord coord) {
        if (Box.CLOSED == flagMap.get(coord))
            flagMap.set(coord, Box.OPENED);
    }

     void setNoBombToFlaggedBox(Coord coord) {
        if (Box.FLAGED == flagMap.get(coord))
            flagMap.set (coord, Box.NOBOMB);
    }

     int getCountOfFlaggedAround(Coord coord) {
        int count = 0;
        for(Coord around : Ranges.getCoordsAround(coord))
            if(flagMap.get(around) == Box.FLAGED)
                    count++;
            return count;
    }
}
