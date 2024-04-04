
processCurrentUser(); // Устанавливаем аутентифицированного пользователя в sessionStorage

function isAuthExist(response) {
    return response.status !== 404;
}

async function processCurrentUser(){
    let response =await fetch("/api/v1/users/current",{
        method: 'GET'
    });
    if(isAuthExist(response)){

    }
    else{
        let avatar = document.getElementById("avatar");
        let signOut = document.getElementById("sign-out");
        let nickname = document.getElementById("nickname");

        avatar.remove();
        signOut.remove();
        nickname.remove();
    }
}










