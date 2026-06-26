function validarCampos(){
    const email = document.getElementById("email");
    const senha = document.getElementById("senha");
    const btn = document.getElementById("btn-logar");
    
    if(email.value.lenght > 0 && senha.value.lenght > 0){
        btn.disabled = false;
    }
}





