import java.util.*;


enum CarType{
    SUV, HATCHBACK
}

class Car{
    private String licensePlate;
    private CarType carType;
    private long entryTime;

    public Car(String licensePlate, CarType carType, long entryTime){
        this.licensePlate = licensePlate;
        this.carType = carType;
        this.entryTime = entryTime;
    }

    public String getLicensePlate(){
        return licensePlate;
    }

    public CarType getCarType(){
        return carType;
    }

    public long getEntryTime(){
        return entryTime;
    }

}

class ParkingLot{
    private int suvCapacity;
    private int hatchbackCapacity;
    private List<Car> suvParking;
    private List<Car> hatchbackParking;
    private Map<String, Car> parkedCars;
    private int suvCount;
    private int hatchbackCount;
    private static final int SUV_RATE = 20;
    private static final int HATCHBACK_RATE = 10;

    public ParkingLot(int suvCapacity, int hatchbackCapacity){
        this.suvCapacity = suvCapacity;
        this.hatchbackCapacity = hatchbackCapacity;
        this.suvParking = new ArrayList<>();
        this.hatchbackParking = new ArrayList<>();
        this.parkedCars = new HashMap<>();
        this.suvCount = 0;
        this.hatchbackCount = 0;
    }

    public void parkCar(String licensePlate, CarType carType){
        long entryTime = System.currentTimeMillis();
        Car car = new Car(licensePlate, carType, entryTime);

        if (carType == carType.HATCHBACK && hatchbackParking.size() < hatchbackCapacity){
            hatchbackParking.add(car);
            hatchbackCount++;
        } else if (carType == CarType.SUV && suvParking.size()< suvCapacity){
            suvParking.add(car);
            suvCount++;
        } else if (carType == CarType.HATCHBACK && suvParking.size() < suvCapacity){
            suvParking.add(car);
            hatchbackCount++;
        } else {
            System.out.println("Parking Full for " + carType);
            return;
        }
        parkedCars.put(licensePlate, car);
        System.out.println(carType + " Car with license Plate " + licensePlate + " parked.");
    }

    public void exitCar(String licensePlate){
        Car car = parkedCars.remove(licensePlate);
        if (car == null){
            System.out.println("Car with license Plate " + licensePlate + " Not Found");
            return;
        }
        long exitTime = System.currentTimeMillis();
        long parkedDuration = (exitTime - car.getEntryTime()) / (1000 * 60 * 60);
        if (parkedDuration == 0) parkedDuration = 1;

        int rate = (car.getCarType() == CarType.SUV) ? SUV_RATE : HATCHBACK_RATE;
        long payment = parkedDuration * rate;

        if (car.getCarType() == CarType.HATCHBACK && hatchbackParking.contains(car)){
            hatchbackParking.remove(car);
            hatchbackCount--;
        } else {
            suvParking.remove(car);
            if (car.getCarType() == CarType.SUV) suvCount--;
            else hatchbackCount--;
        }
        System.out.println("Car With license Plate " + licensePlate + " has to pay: " + payment + " Rupees");
    }

    public void displayParkedCars(){
        System.out.println("SUV parked Cars:");
        for ( Car car : suvParking){
            System.out.println("license Plate: " + car.getLicensePlate());
        }
        System.out.println("Hatchback Parked Cars:");
        for(Car car : hatchbackParking){
            System.out.println("license Plate:" + car.getLicensePlate());
        }
    }
}

public class CarParkingApplication{
    public static void main(String[] args){
        ParkingLot parkingLot = new ParkingLot(5, 5);

        parkingLot.parkCar("xyz123", CarType.HATCHBACK);
        parkingLot.parkCar("asd456", CarType.SUV);
        parkingLot.parkCar("zxc312", CarType.HATCHBACK);
        parkingLot.parkCar("vbn231", CarType.HATCHBACK);
        parkingLot.parkCar("bnm321", CarType.HATCHBACK);
        parkingLot.parkCar("dfg654", CarType.HATCHBACK);

        parkingLot.displayParkedCars();

        parkingLot.exitCar("xyz123");
        parkingLot.exitCar("xyz000");

        parkingLot.displayParkedCars();
    }
}


