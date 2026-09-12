const chart = document.getElementById('dashboardChart');
if (chart && window.Chart) {
    new Chart(chart, {
        type: 'bar',
        data: {
            labels: ['Doanh thu', 'Chi phi', 'Khu vuc'],
            datasets: [{
                label: 'Thong ke HTX',
                data: [Number(chart.dataset.income || 0), Number(chart.dataset.cost || 0), Number(chart.dataset.area || 0)],
                backgroundColor: ['#1ab394', '#f8ac59', '#23c6c8']
            }]
        },
        options: { responsive: true, plugins: { legend: { display: false } } }
    });
}

const toggle = document.getElementById('chatToggle');
const panel = document.getElementById('chatPanel');
if (toggle && panel) {
    toggle.addEventListener('click', () => panel.classList.toggle('open'));
}

const form = document.getElementById('chatForm');
if (form) {
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        const input = document.getElementById('chatInput');
        const messages = document.getElementById('chatMessages');
        const text = input.value.trim();
        if (!text) return;
        messages.insertAdjacentHTML('beforeend', `<div class="me">${text}</div>`);
        input.value = '';
        const res = await fetch('/api/chat', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ message: text })
        });
        const data = await res.json();
        messages.insertAdjacentHTML('beforeend', `<div class="bot">${data.answer}</div>`);
        messages.scrollTop = messages.scrollHeight;
    });
}
