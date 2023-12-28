// Добавим код для обновления времени
function updateClock() {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    const seconds = now.getSeconds().toString().padStart(2, '0');
    const timeString = `${hours}:${minutes}:${seconds}`;
    document.getElementById('clockDisplay').innerText = timeString;
}

// Обновляем часы каждую секунду
setInterval(updateClock, 1000);

// Добавим код для отправки сообщения (просто выводим в консоль)
document.getElementById('messageForm').addEventListener('submit', function (event) {
    event.preventDefault();
    const messageInput = document.getElementById('messageInput');
    const message = messageInput.value.trim();
    if (message !== '') {
        console.log(`You: ${message}`);
        // Здесь можно добавить код для отправки сообщения на сервер и отображения в чате
        messageInput.value = '';
    }
});
