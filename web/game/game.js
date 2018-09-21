const socket = new WebSocket(`ws://${window.location.host}/game`);
socket.onmessage = onMessage;
var currentPlayer = 0;
var player;
var over = false;
console.log("js")

const cells = document.querySelectorAll('img');
const h = document.getElementsByTagName("h1")[0];
const field = document.getElementsByClassName("field")[0];


cells.forEach(cell => {
    cell.addEventListener('click', makeTurn);
    cell.isEmpty = true;
})

initGame();

function initGame(){
    field.style.display = "none";
}

function onMessage(event) {
    const data = JSON.parse(event.data);
    console.log(event.data);
    console.log(data);

    if (data.action === "addPlayer"){
        player = data.id;
        if (player == "0"){
            h.innerHTML = "Hello 1 player!<br>You will play for X!<br>Waiting for 2 player..."
        }
        if (player == "1"){
            h.innerHTML = "Hello 2 player!<br>You will play for O!"
            socket.send(`startGame`);
        }
    }

    else if (data.action === "startGame"){
        field.style.display = "block";
        if (player == "0"){
            h.innerHTML = "Choose cell"
        }
        if (player == "1"){
            h.innerHTML = "Waiting for your turn..."
        }

    }

    else if (data.action === "makeTurn")
    {
        markCell(data.cell);
        if(data.over === "true"){
            over = true;
            if(player == currentPlayer){
                h.innerHTML = "You win!"
            }
            else {
                h.innerHTML = "You lise!"
            }
        } else {
            if(player == currentPlayer){
                h.innerHTML = "Waiting for your turn..."
            } else{
                h.innerHTML = "Your turn. Choose cell"
            }
        }
        currentPlayer = Math.abs(currentPlayer - 1);
    }
}

function makeTurn(event) {
    const value = event.target.getAttribute("data-value");
    if (player == currentPlayer && !over) {
        if (event.target.isEmpty){
            socket.send(`makeTurn:${player}:${value}`);
        }
    }
}

function markCell(cellNumber) {
    console.log(currentPlayer);

    cells.forEach(cell => {
        const value = cell.getAttribute("data-value");
        if (value === cellNumber) {
            if(currentPlayer === 0) {
                cell.src = "game/images/cross.jpg";
                cell.isEmpty = false;

            }
            if(currentPlayer === 1){
                cell.src = "game/images/zero.jpg";
                cell.isEmpty = false;
            }
        }
    })
}