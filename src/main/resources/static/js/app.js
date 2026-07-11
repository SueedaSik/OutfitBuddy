const API_URL = 'http://localhost:8080/api/outfits';

const form = document.getElementById('outfit-form');
const idField = document.getElementById('outfit-id');
const nameField = document.getElementById('name');
const categoryField = document.getElementById('category');
const lastWornField = document.getElementById('lastWorn');
const submitBtn = document.getElementById('submit-btn');
const cancelBtn = document.getElementById('cancel-btn');
const formTitle = document.getElementById('form-title');
const outfitList = document.getElementById('outfit-list');
const emptyMessage = document.getElementById('empty-message');

async function fetchOutfits() {
    const response = await fetch(API_URL);
    if (!response.ok) {
        throw new Error('Outfits konnten nicht geladen werden');
    }
    const outfits = await response.json();
    renderOutfits(outfits);
}

function renderOutfits(outfits) {
    outfitList.innerHTML = '';
    emptyMessage.hidden = outfits.length > 0;

    outfits.forEach((outfit) => {
        const row = document.createElement('tr');

        row.innerHTML = `
            <td>${escapeHtml(outfit.name)}</td>
            <td>${escapeHtml(outfit.category)}</td>
            <td>${outfit.lastWorn ?? '-'}</td>
            <td class="actions">
                <button class="edit-btn" data-id="${outfit.id}">Bearbeiten</button>
                <button class="delete-btn" data-id="${outfit.id}">Löschen</button>
            </td>
        `;

        outfitList.appendChild(row);
    });
}

function escapeHtml(value) {
    const div = document.createElement('div');
    div.textContent = value;
    return div.innerHTML;
}

function resetForm() {
    form.reset();
    idField.value = '';
    submitBtn.textContent = 'Hinzufügen';
    formTitle.textContent = 'Neues Outfit';
    cancelBtn.hidden = true;
}

async function handleSubmit(event) {
    event.preventDefault();

    const outfit = {
        name: nameField.value.trim(),
        category: categoryField.value.trim(),
        lastWorn: lastWornField.value || null,
    };

    const id = idField.value;
    const isEdit = Boolean(id);

    const response = await fetch(isEdit ? `${API_URL}/${id}` : API_URL, {
        method: isEdit ? 'PUT' : 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(outfit),
    });

    if (!response.ok) {
        alert('Speichern fehlgeschlagen.');
        return;
    }

    resetForm();
    await fetchOutfits();
}

async function handleTableClick(event) {
    const target = event.target;

    if (target.classList.contains('delete-btn')) {
        const id = target.dataset.id;
        if (!confirm('Dieses Outfit wirklich löschen?')) {
            return;
        }
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (!response.ok) {
            alert('Löschen fehlgeschlagen.');
            return;
        }
        await fetchOutfits();
    }

    if (target.classList.contains('edit-btn')) {
        const id = target.dataset.id;
        const response = await fetch(`${API_URL}/${id}`);
        if (!response.ok) {
            alert('Outfit konnte nicht geladen werden.');
            return;
        }
        const outfit = await response.json();

        idField.value = outfit.id;
        nameField.value = outfit.name;
        categoryField.value = outfit.category;
        lastWornField.value = outfit.lastWorn ?? '';
        submitBtn.textContent = 'Aktualisieren';
        formTitle.textContent = 'Outfit bearbeiten';
        cancelBtn.hidden = false;
    }
}

form.addEventListener('submit', handleSubmit);
cancelBtn.addEventListener('click', resetForm);
outfitList.addEventListener('click', handleTableClick);

fetchOutfits().catch((err) => console.error(err));
