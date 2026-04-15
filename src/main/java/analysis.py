import pandas as pd
import matplotlib.pyplot as plt

# ===== Wczytanie danych =====
results = pd.read_csv("results.csv")
summary = pd.read_csv("summary.csv")

print("=== RESULTS ===")
print(results.head())

print("\n=== SUMMARY ===")
print(summary)

# ===== Porównanie globalne =====
print("\nŚrednia jakość:")
print(summary.groupby("algorithm")["avg_of_avg"].mean())

print("\nNajlepsze wyniki:")
print(summary.groupby("algorithm")["best_global"].min())

# ===== WYKRESY =====

# 1. średnia jakość
summary.groupby("algorithm")["avg_of_avg"].mean().plot(kind="bar", title="Avg distance")
plt.ylabel("Distance")
plt.show()

# 2. czas
summary.groupby("algorithm")["avg_time"].mean().plot(kind="bar", title="Execution time")
plt.ylabel("ms")
plt.show()

# 3. per plik
for file in summary["file"].unique():
    df = summary[summary["file"] == file]
    df.set_index("algorithm")["avg_of_avg"].plot(kind="bar", title=file)
    plt.show()