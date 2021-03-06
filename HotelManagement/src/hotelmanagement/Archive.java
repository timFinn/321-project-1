/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement;

import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * Archive is a singleton.
 * Has an inner report class that holds information for employee reports
 * Uses an iterator
 * Can be searched with function. 
 * @author James
 */
public class Archive {
    
    
    /////////////////////////////   Setting up Singleton  /////////////////////////////////////
    //singleton
    private static Archive instance = null;
    public ArrayList<Reservation> TheArchives;//holds the list of all the used reservations
 
    
    private Archive()
    {
        TheArchives = new ArrayList();
    }
    
    public static Archive getArchive()
    {
        if(instance == null)
        {
            instance = new Archive();
        }
        return instance;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    
    /////////////////////////////    Setting up Iterator    ////////////////////////////////////
    
    private static class StepThrough implements Iterator{ 

        private ArrayList<Reservation> thoseArchives;
        private int i = 0;
        
        public StepThrough(Archive thoseArchives) {
            this.thoseArchives = thoseArchives.TheArchives;
        }
        
        @Override
        public Object next(){
            return thoseArchives.get(i++);
        }

        @Override
        public boolean hasNext() {
            return (i < thoseArchives.size());            
        }
        
        
    }	
    
    public Iterator stepThrough() {
            return new StepThrough(this);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Adds a reservation to the archives. Does NOT delete it from
     * the reservations arraylist
     * @param reservation reservation to be archived
     * @return success or failure of adding it to the array list
     */
    public boolean archiveThisReservation(Reservation reservation) throws NullPointerException
    {
       if (reservation != null)
       {
           return (TheArchives.add(reservation));
       }
       else
           throw new NullPointerException();
    }
    
    
    public ArrayList<Reservation> searchArchives()
    {
        
        return null;
    }
    
    
    /**
     *
     * @return Report Object containing information fields
     */
    public Report getReport()
    {
        return new Report();
    }
    
    /*
     * Holds information to be used for displaying reports
     * such as total checkins and total income
    */
    public class Report
    {
        private RoomTypeEnum MostOccupiedRoom;
        private int MostOccupiedRoomAmount;
        private int TotalCheckins;
        private double totalIncome;
        
        Report()
        {
            RefreshData();
        }
        
        /**
         * Runs method to get up-to-date room type
         * that is most often used then returns it
         * as a string
         * @return Most used room type as string. Must convert back to enum outside
         */
        public RoomTypeEnum getMostOccupiedRoom() 
        {
            setMostOccupiedRoom();
            return MostOccupiedRoom;
        }

        /**
         * Counts the total amount of check-ins to date
         * then returns as an int
         * @return total amount of check-ins
         */
        public int getTotalCheckins() 
        {
            setTotalCheckins();
            return TotalCheckins;
        }

        /**
         * Runs method to get the up-to-date income
         * then returns that amount to caller
         * @return total income earned
         */
        public double getTotalIncome() 
        {
            setTotalIncome();
            return totalIncome;
        }

        private void setMostOccupiedRoom() 
        {
            MostOccupiedRoomAmount = 0;
            for (Reservation res : TheArchives)
            {
                for (RoomTypeEnum RoomType : RoomTypeEnum.values())
                {
                    int count = 0;
                    if (res.getRoom().getType().equals(RoomType))
                    {
                        count++;
                    }
                    if(count > MostOccupiedRoomAmount)
                    {
                        MostOccupiedRoom = RoomType;
                        MostOccupiedRoomAmount = count;
                    }
                    
//                    if(Collections.frequency(TheArchives, RoomType) > Collections.frequency(TheArchives, RoomTypeEnum.valueOf(MostOccupiedRoom)))
//                    {
//                        MostOccupiedRoom = RoomType.toString();
//                        MostOccupiedRoomAmount = Collections.frequency(TheArchives, RoomType);
//                    }
                }    
            }
        }

        private void setTotalCheckins() 
        {
            TotalCheckins = 0;
            for (Reservation res : TheArchives)
            {
                TotalCheckins++;
            }
        }

        private void setTotalIncome() 
        {
            totalIncome = 0.0000;
            for (Reservation res : TheArchives)
            {
                totalIncome += (res.getTotalPrice());
            } 
        }
                  
        private void RefreshData()
        {
            setMostOccupiedRoom();
            setTotalCheckins();
            setTotalIncome();
        }

        /**
         * updates the mostOccupiedRoom property and returns the
         * amount as an int
         * @return amount of checkins for most occupied room
         */
        public int getMostOccupiedRoomAmount() 
        {
            setMostOccupiedRoom();
            return MostOccupiedRoomAmount;
        }
        
        private void setTotalCheckins(RoomTypeEnum roomType, int month) 
        {
            TotalCheckins = 0;
            for (Reservation res : TheArchives)
            {
                if(res.getRoom().getType().equals(roomType) && (res.getStartDate().getMonth() == (month - 1)))
                {
                    TotalCheckins++;
                }
            }
        }
        
        /**
         * Same as the default except filters by roomtype and month.
         * @param roomType type of room in room object
         * @param month integer corresponding to month of the gregorian year. January = 1 , December = 12
         * @return total amount of checkins for the month and roomtype
         */
        public int getTotalCheckins(RoomTypeEnum roomType, int month)
        {
            setTotalCheckins(roomType, month);
            return TotalCheckins;
        }
        
        private void setTotalIncome(RoomTypeEnum roomType, int month) 
        {
            totalIncome = 0.0000;
            for (Reservation res : TheArchives)
            {
                if(res.getRoom().getType().equals(roomType) && (res.getStartDate().getMonth() == (month - 1)))
                {
                    totalIncome += (res.getTotalPrice());
                }
            } 
        }       
        

        /**
         * Same as the default except filters by roomtype and month
         * @param roomType type of room in room object
         * @param month integer corresponding to the month of the gregorian year. January = 1 , December = 12
         * @return total income earned from the roomtype and month
         */
        public double getTotalIncome(RoomTypeEnum roomType, int month)
        {
            setTotalIncome(roomType, month);
            return totalIncome;
        }
        
        private void setMostOccupiedRoom(RoomTypeEnum roomType, int month)
        {
            for (Reservation res : TheArchives)
            {
                if(res.getRoom().getType().equals(roomType) && (res.getStartDate().getMonth() == (month - 1)))
                {
                    for (RoomTypeEnum RoomType : RoomTypeEnum.values())
                    {
                        if(RoomType == roomType)
                        {
                            int count = 0;
                            if (res.getRoom().getType().equals(roomType))
                            {
                                count++;
                            }
                            if(count > MostOccupiedRoomAmount)
                            {
                                MostOccupiedRoom = RoomType;
                                MostOccupiedRoomAmount = count;
                            }                            

//                            if(Collections.frequency(TheArchives, RoomType) > Collections.frequency(TheArchives, RoomTypeEnum.valueOf(MostOccupiedRoom)))
//                            {
//                                MostOccupiedRoom = RoomType.toString();
//                                MostOccupiedRoomAmount = Collections.frequency(TheArchives, RoomType);
//                            }
                        }
                    }    
                }
            }
        }
        
        /**
         * Same as the default except filters by roomtype and month
         * @param roomType Roomtype in room object
         * @param month integer corresponding to the month of the gregorian year. January = 1 , December = 12
         * @return Most used room type as string. Must convert back to enum outside
         */
        public RoomTypeEnum getMostOccupied(RoomTypeEnum roomType, int month)
        {
            setMostOccupiedRoom(roomType, month);
            return MostOccupiedRoom;
        }
        
    }
    
    
    
    
}
