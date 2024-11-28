let processInstanceKey;
let success;


function findRestaurant() {
    const address = document.getElementById('address').value.trim();

    if (!address) {
        alert('Proszę wpisać adres.');
        return;
    }

    fetch('/start', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ address: address })
    })
        .then(response => response.json())
        .then(data => {
            console.log(data); // Tu pojawią się teraz dane: isRestaurant, restaurants

            if (data.isRestaurant === true && data.restaurants && data.restaurants.length > 0) {
                localStorage.setItem('foundRestaurants', JSON.stringify(data.restaurants));
                window.location.href = '/restaurants.html';
            } else {
                alert('Nie znaleziono restauracji w podanej lokalizacji. Spróbuj ponownie.');
            }
        })
        .catch(error => {
            console.error(error);
            alert('Wystąpił błąd podczas wyszukiwania restauracji.');
        });
}
