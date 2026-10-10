const API_URL = "/api/expenses";
const TOTAL_API_URL = "/api/expenses/total";
const CATEGORY_API_URL = "/api/expenses/summary/category";


const expenseForm = document.getElementById("expenseForm");
const expenseTableBody = document.getElementById("expenseTableBody");
const totalExpensesElement = document.getElementById("totalExpenses");
const totalCategoriesElement = document.getElementById("totalCategories");
const categorySummaryElement = document.getElementById("categorySummary");
const formMessage = document.getElementById("formMessage");
const searchInput = document.getElementById("searchInput");
const searchButton = document.getElementById("searchButton");
const resetButton = document.getElementById("resetButton");
const submitButton = document.getElementById("submitButton");


let editingExpenseId = null;

function formatCurrency(amount) {
    return new Intl.NumberFormat("en-IN", {
        style: "currency",
        currency: "INR"
    }).format(amount);
}

function showMessage(message, isError = false) {
    formMessage.textContent = message;
    formMessage.className = isError
        ? "error-message"
        : "success-message";
}

async function apiRequest(url, options = {}) {
    const response = await fetch(url, options);

    if (!response.ok) {
        const errorText = await response.text();

        throw new Error(
            errorText || `Request failed with status ${response.status}`
        );
    }

    if (response.status === 204) {
        return null;
    }

    const contentType = response.headers.get("content-type");

    if (contentType && contentType.includes("application/json")) {
        return response.json();
    }

    return null;
}

async function loadDashboard() {
    try {
        await Promise.all([
            loadTotalExpenses(),
            loadCategorySummary()
        ]);
    } catch (error) {
        console.error("Dashboard loading error:", error);
    }
}


async function loadTotalExpenses() {
    const data = await apiRequest(TOTAL_API_URL);

    totalExpensesElement.textContent =
        formatCurrency(data.totalExpenses);
}

async function loadExpenses() {
    try {
        const expenses = await apiRequest(API_URL);

        displayExpenses(expenses);
    } catch (error) {
        console.error("Expense loading error:", error);

        expenseTableBody.innerHTML = `
            <tr>
                <td colspan="7">Unable to load expenses.</td>
            </tr>
        `;
    }
}


function displayExpenses(expenses) {
    expenseTableBody.replaceChildren();

    if (!expenses || expenses.length === 0) {
        const row = document.createElement("tr");
        const cell = document.createElement("td");

        cell.colSpan = 7;
        cell.textContent = "No expenses found.";

        row.appendChild(cell);
        expenseTableBody.appendChild(row);

        return;
    }

    expenses.forEach(expense => {
        const row = document.createElement("tr");

        addCell(row, expense.id);
        addCell(row, formatCurrency(expense.amount));
        addCell(row, expense.category);
        addCell(row, expense.description);
        addCell(row, expense.date);
        addCell(row, expense.paymentMethod);

        const actionsCell = document.createElement("td");

        const editButton = document.createElement("button");
        editButton.textContent = "Edit";
        editButton.className = "edit-button";

        editButton.addEventListener("click", () => {
            editExpense(expense);
        });

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.className = "delete-button";

        deleteButton.addEventListener("click", () => {
            deleteExpense(expense.id);
        });

        actionsCell.append(editButton, deleteButton);
        row.appendChild(actionsCell);

        expenseTableBody.appendChild(row);
    });
}


function addCell(row, value) {
    const cell = document.createElement("td");

    cell.textContent = value ?? "";

    row.appendChild(cell);
}


async function loadCategorySummary() {
    const summary = await apiRequest(CATEGORY_API_URL);

    categorySummaryElement.replaceChildren();

    const categories = Object.entries(summary || {});

    totalCategoriesElement.textContent = categories.length;

    if (categories.length === 0) {
        categorySummaryElement.textContent =
            "No category data available.";

        return;
    }

    categories.forEach(([category, amount]) => {
        const card = document.createElement("div");
        card.className = "category-card";

        const heading = document.createElement("h3");
        heading.textContent = category;

        const total = document.createElement("p");
        total.textContent = formatCurrency(amount);

        card.append(heading, total);
        categorySummaryElement.appendChild(card);
    });
}


expenseForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const expense = {
        amount: Number(document.getElementById("amount").value),
        category: document.getElementById("category").value.trim(),
        description: document.getElementById("description").value.trim(),
        date: document.getElementById("date").value,
        paymentMethod: document.getElementById("paymentMethod").value
    };

    if (
        !Number.isFinite(expense.amount) ||
        expense.amount <= 0 ||
        !expense.category ||
        !expense.description ||
        !expense.date ||
        !expense.paymentMethod
    ) {
        showMessage(
            "Please enter valid details in every field.",
            true
        );

        return;
    }

    const isEditing = editingExpenseId !== null;

    const url = isEditing
        ? `${API_URL}/${editingExpenseId}`
        : API_URL;

    const method = isEditing ? "PUT" : "POST";

    submitButton.disabled = true;

    try {
        await apiRequest(url, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(expense)
        });

        showMessage(
            isEditing
                ? "Expense updated successfully!"
                : "Expense added successfully!"
        );

        resetExpenseForm();

        await Promise.all([
            loadExpenses(),
            loadDashboard()
        ]);
    } catch (error) {
        console.error("Saving expense error:", error);

        showMessage(
            `Unable to save expense: ${error.message}`,
            true
        );
    } finally {
        submitButton.disabled = false;
    }
});


function editExpense(expense) {
    editingExpenseId = expense.id;

    document.getElementById("amount").value = expense.amount;
    document.getElementById("category").value = expense.category;
    document.getElementById("description").value = expense.description;
    document.getElementById("date").value = expense.date;
    document.getElementById("paymentMethod").value =
        expense.paymentMethod;

    submitButton.textContent = "Update Expense";

    showMessage(`Editing expense ID ${expense.id}.`);

    expenseForm.scrollIntoView({
        behavior: "smooth",
        block: "start"
    });
}

function resetExpenseForm() {
    expenseForm.reset();

    editingExpenseId = null;

    submitButton.textContent = "Add Expense";
}


async function deleteExpense(id) {
    const confirmed = window.confirm(
        `Are you sure you want to delete expense ID ${id}?`
    );

    if (!confirmed) {
        return;
    }

    try {
        await apiRequest(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        showMessage("Expense deleted successfully!");

        await Promise.all([
            loadExpenses(),
            loadDashboard()
        ]);
    } catch (error) {
        console.error("Deleting expense error:", error);

        showMessage(
            `Unable to delete expense: ${error.message}`,
            true
        );
    }
}

async function searchExpenses() {
    const query = searchInput.value.trim().toLowerCase();

    try {
        const expenses = await apiRequest(API_URL);

        const filteredExpenses = expenses.filter(expense =>
            (expense.category || "").toLowerCase().includes(query) ||
            (expense.description || "").toLowerCase().includes(query)
        );

        displayExpenses(filteredExpenses);
    } catch (error) {
        console.error("Search error:", error);

        showMessage("Unable to search expenses.", true);
    }
}


searchButton.addEventListener("click", searchExpenses);


searchInput.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        event.preventDefault();
        searchExpenses();
    }
});


resetButton.addEventListener("click", function () {
    searchInput.value = "";
    loadExpenses();
});


async function initializeApp() {
    await Promise.all([
        loadExpenses(),
        loadDashboard()
    ]);
}

initializeApp();