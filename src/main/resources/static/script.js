let restaurants = [];

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
            console.log(data);
            processInstanceKey = data.processInstanceKey;
            return fetch('http://localhost:8080/api/restaurantAddress/searchRestaurant?address=' + address, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            restaurants = data;
            displayRestaurants(restaurants);
        })
        .catch(error => {
            console.error(error);
            alert('Wystąpił błąd podczas wyszukiwania restauracji.');
        });
}
function displayRestaurants(restaurants) {
    const resultsContainer = document.getElementById('results');
    resultsContainer.innerHTML = '';

    if (restaurants.length === 0) {
        resultsContainer.innerHTML = '<p>Nie znaleziono restauracji.</p>';
        return;
    }

    const ul = document.createElement('ul');
    restaurants.forEach(restaurant => {
        const li = document.createElement('li');
        li.textContent = `${restaurant.name}, ${restaurant.distance}`;
        ul.appendChild(li);
    });

    resultsContainer.appendChild(ul);
}

