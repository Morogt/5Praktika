import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Item {
    private final String name;
    private final int maxStackSize; // Максимальный размер стака
    private int stackSize; // Текущий размер стака

    public Item(String name, int maxStackSize) {
        this.name = name;
        this.maxStackSize = maxStackSize;
        this.stackSize = 1; // По умолчанию у каждого предмета размер стака равен 1
    }

    public String getName() {
        return name;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getStackSize() {
        return stackSize;
    }

    public void increaseStackSize() {
        if (stackSize < maxStackSize) {
            stackSize++;
        }
    }

    public void decreaseStackSize() {
        if (stackSize > 1) {
            stackSize--;
        }
    }

    @Override
    public String toString() {
        return name + " (Максимум: " + maxStackSize + ", Количество: " + stackSize + ")";
    }
}

class Inventory {
    private final List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        boolean added = false;
        for (Item existingItem : items) {
            if (existingItem.getName().equals(item.getName()) && existingItem.getStackSize() < existingItem.getMaxStackSize()) {
                existingItem.increaseStackSize();
                added = true;
                break;
            }
        }
        if (!added) {
            items.add(item);
        }
    }

    public void removeItem(Item item) {
        item.decreaseStackSize();
        if (item.getStackSize() == 1) {
            items.remove(item);
        }
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "Пустой инвентарь";
        } else {
            StringBuilder sb = new StringBuilder("Инвентарь:\n");
            for (Item item : items) {
                sb.append("- ").append(item).append("\n");
            }
            return sb.toString();
        }
    }
}

class Person {
    private final String name;
    private final Inventory inventory;

    public Person(String name) {
        this.name = name;
        this.inventory = new Inventory();
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class Main {
    private static final Map<String, Person> people = new HashMap<>();
    private static final List<Item> items = new ArrayList<>();

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("Меню:");
            System.out.println("1. Просмотр всех персонажей");
            System.out.println("2. Добавление нового персонажа");
            System.out.println("3. Удаление персонажа");
            System.out.println("4. Просмотр инвентаря конкретного персонажа");
            System.out.println("5. Редактирование инвентаря персонажа: добавление, удаление предметов");
            System.out.println("6. Просмотр всех предметов");
            System.out.println("7. Редактирование данных отдельного предмета");
            System.out.println("8. Удаление предмета из системы");
            System.out.println("9. Добавление предмета в систему");
            System.out.println("0. Выйти");

            System.out.print("Введите номер выбранного пункта: ");

            while (true) {
                try {
                    Scanner scanner = new Scanner(System.in);
                    choice = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Введены некорректные данные, нужно ввести целое положительное число");
                }
            }


            switch (choice) {
                case 1 -> viewAllPeople();
                case 2 -> addPerson();
                case 3 -> deletePerson();
                case 4 -> viewPersonInventory();
                case 5 -> editPersonInventory();
                case 6 -> viewAllItems();
                case 7 -> editItem();
                case 8 -> deleteItem();
                case 9 -> addItem();
                case 0 -> System.out.println("Выход");
                default -> System.out.println("Некорректный выбор. Попробуйте снова. Нужно ввести целое положительное число");
            }
        } while (choice != 0);
    }

    private static void viewAllPeople() {
        System.out.println("Список всех персонажей:");
        for (Person person : people.values()) {
            System.out.println(person.getName());
        }
    }

    private static void addPerson() {
        System.out.print("Введите имя нового персонажа: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        Person person = new Person(name);
        people.put(name, person);
        System.out.println("Персонаж " + name + " добавлен.");
    }

    private static void deletePerson() {
        System.out.print("Введите имя персонажа, которого хотите удалить: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        Person person = people.get(name);
        if (person != null) {
            people.remove(name);
            System.out.println("Персонаж " + name + " удален.");
        } else {
            System.out.println("Персонаж " + name + " не найден.");
        }
    }

    private static void viewPersonInventory() {
        System.out.print("Введите имя персонажа, инвентарь которого хотите посмотреть: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        Person person = people.get(name);
        if (person != null) {
            Inventory inventory = person.getInventory();
            System.out.println(person.getName() + " имеет следующий инвентарь:\n" + inventory);
        } else {
            System.out.println("Персонаж " + name + " не найден.");
        }
    }

    private static void editPersonInventory() {
        System.out.print("Введите имя персонажа, инвентарь которого хотите редактировать: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        Person person = people.get(name);
        if (person != null) {
            Inventory inventory = person.getInventory();
            int choice;

            do {
                System.out.println("Редактирование инвентаря для " + name + ":");
                System.out.println("1. Добавить предмет");
                System.out.println("2. Удалить предмет");
                System.out.println("0. Вернуться в меню");

                System.out.print("Введите номер выбранного пункта: ");

                while (true) {
                    try {
                        Scanner scanner1 = new Scanner(System.in);
                        choice = scanner1.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("Введены некорректные данные, нужно ввести целое положительное число");
                    }
                }

                switch (choice) {
                    case 1:
                        addItemToInventory(inventory);
                        break;
                    case 2:
                        removeItemFromInventory(inventory);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Некорректный выбор. Попробуйте снова.");
                }
            } while (choice != 0);
        } else {
            System.out.println("Персонаж " + name + " не найден.");
        }
    }

    private static void viewAllItems() {
        System.out.println("Список всех предметов:");
        for (Item item : items) {
            System.out.println(item.getName());
        }
    }

    private static void editItem() {
        System.out.print("Введите имя предмета, который хотите редактировать: ");
        Scanner scanner = new Scanner(System.in);
        String itemName = scanner.next();
        Item item = findItem(itemName);
        if (item != null) {
            System.out.print("Введите новое имя для предмета: ");
            String newItemName = scanner.next();
            int stackSize;
            while (true) {
                try {
                    System.out.print("Введите новое максимальное количество предметов в стаке: ");
                    Scanner scanner1 = new Scanner(System.in);
                    stackSize = scanner1.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Введены некорректные данные, нужно ввести целое положительное число");
                }
            }
            item = new Item(newItemName, stackSize);
            System.out.println("Предмет " + itemName + " изменен на " + newItemName + " с максимальным количество в стаке: " + stackSize);
        } else {
            System.out.println("Предмет " + itemName + " не найден.");
        }
    }

    private static void deleteItem() {
        System.out.print("Введите имя предмета, который хотите удалить: ");
        Scanner scanner = new Scanner(System.in);
        String itemName = scanner.next();
        Item item = findItem(itemName);
        if (item != null) {
            items.remove(item);
            for (Person person : people.values()) {
                Inventory inventory = person.getInventory();
                inventory.getItems().removeIf(i -> i.getName().equals(itemName));
            }
            System.out.println("Предмет " + itemName + " удален.");
        } else {
            System.out.println("Предмет " + itemName + " не найден.");
        }
    }

    private static void addItem() {
        System.out.print("Введите имя нового предмета: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        Item item = findItem(name);
        if (item != null) {
            System.out.println("Предмет с таким названием уже добавлен");
        } else {
            System.out.print("Введите максимальный размер стака для предмета: ");
            int stackSize;
            while (true) {
                try {
                    Scanner scanner1 = new Scanner(System.in);
                    stackSize = scanner1.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Введены некорректные данные, нужно ввести целое положительное число");
                }
            }
            Item item1 = new Item(name, stackSize);
            items.add(item1);
            System.out.println("Предмет " + name + " добавлен.");
        }

    }

    private static void addItemToInventory(Inventory inventory) {
        System.out.print("Введите имя предмета, который хотите добавить: ");
        Scanner scanner = new Scanner(System.in);
        String itemName = scanner.next();
        Item item = findItem(itemName);
        if (item != null) {
            inventory.addItem(item);
            System.out.println("Предмет " + itemName + " добавлен в инвентарь.");
        } else {
            System.out.println("Предмет " + itemName + " не найден.");
        }
    }

    private static void removeItemFromInventory(Inventory inventory) {
        System.out.print("Введите имя предмета, который хотите удалить: ");
        Scanner scanner = new Scanner(System.in);
        String itemName = scanner.next();
        Item item = findItem(itemName);
        if (item != null) {
            inventory.removeItem(item);
            System.out.println("Предмет " + itemName + " удален из инвентаря.");
        } else {
            System.out.println("Предмет " + itemName + " не найден.");
        }
    }

    private static Item findItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

}
