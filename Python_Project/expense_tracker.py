import csv
import os
from collections import defaultdict
import matplotlib.pyplot as plt

FILE = "expenses.csv"

# ---------- Add Expense ----------
def add_expense():
    date = input("Enter date (dd-mm-yyyy): ")
    category = input("Enter category (Food / Travel / Bills / Shopping / Others): ")
    
    try:
        amount = float(input("Enter amount: "))
    except:
        print("Invalid amount.")
        return
    
    desc = input("Enter description: ")

    # ensure proper newline before writing
    if os.path.exists(FILE):
        with open(FILE, 'a+') as f:
            f.seek(0, os.SEEK_END)
            if f.tell() > 0:
                f.seek(f.tell() - 1)
                if f.read(1) != '\n':
                    f.write('\n')

    file_exists = os.path.isfile(FILE)

    with open(FILE, mode='a', newline='') as file:
        writer = csv.writer(file)

        if not file_exists:
            writer.writerow(["date", "category", "amount", "description"])

        writer.writerow([date, category, amount, desc])

    print("Expense added successfully.")


# ---------- View Expenses ----------
def view_expenses():
    if not os.path.exists(FILE):
        print("No expenses found.")
        return

    with open(FILE, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            print(" | ".join(row))


# ---------- Monthly Summary ----------
def monthly_summary():
    if not os.path.exists(FILE):
        print("No data available.")
        return

    month = input("Enter month (mm-yyyy): ")

    total = 0
    category_total = defaultdict(float)

    with open(FILE, 'r') as file:
        reader = csv.DictReader(file)

        for row in reader:
            # extracting mm-yyyy from dd-mm-yyyy
            if row['date'][3:] == month:
                amt = float(row['amount'])
                total += amt
                category_total[row['category']] += amt

    print("\nTotal Expense:", total)

    print("\nCategory-wise spending:")
    for cat, amt in category_total.items():
        print(cat, ":", amt)

    if category_total:
        highest = max(category_total, key=category_total.get)
        print("\nHighest Spending Category:", highest)
    else:
        print("No data for this month.")


# ---------- Pie Chart ----------
def show_pie_chart():
    if not os.path.exists(FILE):
        print("No data available.")
        return

    month = input("Enter month (mm-yyyy): ")

    category_total = defaultdict(float)

    with open(FILE, 'r') as file:
        reader = csv.DictReader(file)

        for row in reader:
            # filter by month
            if row['date'][3:] == month:
                category_total[row['category']] += float(row['amount'])

    if not category_total:
        print("No data for this month.")
        return

    labels = list(category_total.keys())
    values = list(category_total.values())

    plt.pie(values, labels=labels, autopct='%1.1f%%')
    plt.title(f"Expense Distribution for {month}")
    plt.show()


# ---------- Main Menu ----------
def main():
    while True:
        print("\n--- Expense Tracker ---")
        print("1. Add Expense")
        print("2. View Expenses")
        print("3. Monthly Summary")
        print("4. Show Pie Chart")
        print("5. Exit")

        choice = input("Enter choice: ")

        if choice == '1':
            add_expense()
        elif choice == '2':
            view_expenses()
        elif choice == '3':
            monthly_summary()
        elif choice == '4':
            show_pie_chart()
        elif choice == '5':
            print("Exiting...")
            break
        else:
            print("Invalid choice")


if __name__ == "__main__":
    main()