import java.util.Scanner;
//stores the state of the machine
enum State {
    ACTION("choosing an ACTION"), COFFEE_CHOICE("choosing a variant of coffee"), FILL_WATER("fill water"),
        FILL_MILK("fill milk"), FILL_COFFEE("fill coffee"), FILL_CUP("fill cup");
    String name;
    State(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }
}

class Coffee {
    int water, milk, coffeeBeans, price;
 
    Coffee(int water, int milk, int coffeeBeans, int price) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.price = price;
      }
    int getWater() {
        return this.water;
    }
    int getMilk() {
        return this.milk;
    }
    int getCoffeeBeans() {
        return this.coffeeBeans;
    }
    int getPrice() {
        return this.price;
    }
}

//the main class
public class CoffeeMachine {
    static State state = State.ACTION;

  /*
	For the espresso, the coffee machine needs 250 ml of water and 16 g of coffee beans. It costs $4.
	For the latte, the coffee machine needs 350 ml of water, 75 ml of milk, and 20 g of coffee beans. It costs $7.
	And for the cappuccino, the coffee machine needs 200 ml of water, 100 ml of milk, and 12 g of coffee. It costs $6
	*/
    static Coffee espresso = new Coffee(250, 0, 16, 4);
    static Coffee latte = new Coffee(350, 75, 20, 7);
    static Coffee cappuccino = new Coffee(200, 100, 12, 6);
  
	static Coffee selectedCoffee=null;

    static int cups = 9;
    static int money = 550;
    static int water_level = 400;
    static int milk_level = 540;
    static int coffee_level = 120;

    public static void main(String[] a) {
    
        Scanner sc = new Scanner(System.in);
        String s = null;

        while (!(s = sc.nextLine()).equals("exit")) {
            process(s);
        }

    }

    public static void process(String str) {
        switch (str) {
            case "remaining":
                state = State.ACTION;
                printStatus();
				selectedCoffee=null;
                System.out.println("Write ACTION (buy, fill, take, remaining, exit):");
                break;
            case "back":
                state = State.ACTION;
				selectedCoffee=null;
                System.out.println("Write ACTION (buy, fill, take, remaining, exit):");
                break;
            case "buy":
                state = State.COFFEE_CHOICE;
				selectedCoffee=null;
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                break;
            case "fill":
                state = State.FILL_WATER;
				selectedCoffee=null;
                System.out.println("Write how many ml of water the coffee machine has:");
                break;
            case "take":
                state = State.ACTION;
				selectedCoffee=null;
                System.out.println("I gave you $" + money);
                money = 0;
                System.out.println("Write ACTION (buy, fill, take, remaining, exit):");
                break;
            default:
				selectedCoffee=null;
                try {
                    int num = Integer.parseInt(str);
                    switch (state) {
                        case FILL_WATER:
                            water_level += num;
	                        System.out.println("Write how many ml of milk the coffee machine has:");
                            state = State.FILL_MILK;
                            break;
                        case FILL_MILK:
                            milk_level += num;
                            state = State.FILL_COFFEE;
                            System.out.println("Write how many grams of coffee beans the coffee machine has:");
                            break;
                        case FILL_COFFEE:
                            coffee_level += num;
                            System.out.println("Write how many disposable cups of coffee do you want to add:");
                            state = State.FILL_CUP;
                            break;
                        case FILL_CUP:
                            cups += num;
                            System.out.println("Write ACTION (buy, fill, take, remaining, exit):");
                            state = State.ACTION;
                            break;
                        case COFFEE_CHOICE:
                            buyCoffee(num);
                            state = State.ACTION;
                            break;
                    }

                } catch (Exception e) {}

        }


    }

	/**
	* Returns true is there are enough ingredients. 
	*
	* @param  milk  
	* @param  water
	* @param  coffee
	* @return      true 
	*/
    static boolean checkLevels(int milk, int water, int coffee) {
        if (water_level - water < 0) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (milk_level - milk < 0) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (coffee_level - coffee < 0) {
            System.out.println("Sorry, not enough coffee!");
            return false;
        }
        System.out.println("I have enough resources, making you a coffee!");
        return true;
    }

    static void buyCoffee(int c) {

        if (c == 1 && checkLevels(milk_level, 250, 16)) {
			selectedCoffee=espresso;
            reduceLevels(selectedCoffee);
        } else if (c == 2 && checkLevels(75, 350, 20)) {
			selectedCoffee=latte;
            reduceLevels(selectedCoffee);
        } else if (c == 3 && checkLevels(250, 200, 12)) {
			selectedCoffee=cappuccino;
            reduceLevels(selectedCoffee);
        }
    }
	
	static void reduceLevels(Coffee coffee){
			water_level -=coffee.getWater();
            milk_level -= coffee.getMilk();
            coffee_level -= coffee.getCoffeeBeans();
            money += coffee.getPrice();
            cups -= 1;
	}

    public static void printStatus() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(water_level + " of water");
        System.out.println(milk_level + " of milk");
        System.out.println(coffee_level + " of coffee beans");
        System.out.println(cups + " of disposable cups");
        if (money > 0)
            System.out.print("$");
        System.out.println(money + " of money");
        System.out.println();
    }

}
