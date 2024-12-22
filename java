function newgame() {
    window.location.href = "game1.html";
}

const gameBoard = document.querySelector("#gameBoard");
const ctx = gameBoard.getContext("2d");
const scoreText = document.querySelector("#scoreText");
const resetBtn = document.querySelector("#resetBtn");

const gamewidth = gameBoard.width;
const gameheight = gameBoard.height;
const unitsize = 20;

let xvelocity = unitsize;
let yvelocity = 0;
let foodX;
let foodY;
let score = 0;
let snake = [{x: 0, y: 0}, {x: unitsize, y: 0}, {x: unitsize * 2, y: 0}];

gameStart();

window.addEventListener("keydown", changedirection);
resetBtn.addEventListener("click", resetgame);

function gameStart() {
    setInterval(nexttick, 100);
}

function nexttick() {
    clearBoard();
    drawFood();
    moveSnake();
    drawSnake();
    checkGameOver();
    updateScore();
}

function clearBoard() {
    ctx.clearRect(0, 0, gamewidth, gameheight);
}

function createfood() {
    function randomFood(min, max) {
        const randNum = Math.round((Math.random() * (max - min) + min) / unitsize) * unitsize;
        return randNum;
    }
    foodX = randomFood(0, gamewidth - unitsize);
    foodY = randomFood(0, gameheight - unitsize);
}

function drawFood() {
    ctx.fillStyle = "blue"; 
    ctx.fillRect(foodX, foodY, unitsize, unitsize);
}

function moveSnake() {
    const head = { x: snake[0].x + xvelocity, y: snake[0].y + yvelocity };
    snake.unshift(head);

    if (snake[0].x === foodX && snake[0].y === foodY) {
        score += 10;
        createfood();
    } else {
        snake.pop();
    }
}

function drawSnake() {
    ctx.fillStyle = "white"; 
    snake.forEach((segment) => {
        ctx.fillRect(segment.x, segment.y, unitsize, unitsize);
    });
}

function changedirection(event) {
    if (event.keyCode === 37 && xvelocity === 0) {
        xvelocity = -unitsize;
        yvelocity = 0;
    } else if (event.keyCode === 38 && yvelocity === 0) {
        xvelocity = 0;
        yvelocity = -unitsize;
    } else if (event.keyCode === 39 && xvelocity === 0) {
        xvelocity = unitsize;
        yvelocity = 0;
    } else if (event.keyCode === 40 && yvelocity === 0) {
        xvelocity = 0;
        yvelocity = unitsize;
    }
}

function checkGameOver() {
    if (snake[0].x < 0 || snake[0].x >= gamewidth || snake[0].y < 0 || snake[0].y >= gameheight) {
        displayGameOver();
    }

    for (let i = 1; i < snake.length; i++) {
        if (snake[0].x === snake[i].x && snake[0].y === snake[i].y) {
            displayGameOver();
        }
    }
}

function displayGameOver() {
    ctx.clearRect(0, 0, gamewidth, gameheight);
    ctx.fillStyle = "white";
    ctx.font = "30px Arial";
    ctx.fillText("Game Over", gamewidth / 3, gameheight / 2);
}

function resetgame() {
    score = 0;
    xvelocity = unitsize;
    yvelocity = 0;
    snake = [{ x: 0, y: 0 }, { x: unitsize, y: 0 }, { x: unitsize * 2, y: 0 }];
    createfood();
}

function updateScore() {
    scoreText.textContent = score;
}
