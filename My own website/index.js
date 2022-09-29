let index = 1;
changeImage = function(){
    let imgs = ['pic/pic1.jpg', 'pic/pic2.jpg', 'pic/pic3.jpg'];
    document.getElementById('img').style.transition = "all 2s";
    document.getElementById('img').src = imgs[index];
    index ++;
    if(index == 3){
        index = 0;
    }
}
setInterval(changeImage, 3000);

// let x = prompt("Enter your name: ");
// alert("Hello " + x);

function move(){
    const element = document.getElementById('myBar');
    let width = 0;
    let id = setInterval(frame, 10);
    function frame(){
        if(width == 100){
            clearInterval(id);
            document.getElementById('message').innerHTML = "You learned 100% the knowledge of creating a website";
        } else {
            width ++;
            element.style.width = width + '%';
        }
    }
}