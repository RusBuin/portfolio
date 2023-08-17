
export default function processOpnFrmData(event){
    
    event.preventDefault();
        const nopName = document.getElementById("name").value.trim();
       
        const nopOpn = document.getElementById("nazor").value.trim();
        const nopEmail = document.getElementById("email").value.trim();
     
        const vysledok = document.getElementById("vysledok").checked;
        const slovo = document.getElementById("slovo").value.trim();
        const imageValue = document.getElementById("url").value;
        var rod;

     var ele=document.getElementsByName('rod');     

     for(var i = 0; i < ele.length; i++) {
         if(ele[i].checked){
             rod=ele[i].value;
             break;
            } 
     }


   
    if(nopName==="" || nopOpn===""){
        window.alert("Please, enter both your name and opinion");
        return;
    }

    
    const newOpinion =
        {
            nopName,
            nopOpn,
            nopEmail,
            vysledok,
            rod,
            imageValue,
            slovo,
            created: new Date()

        };


    let opinions = [];

    if(localStorage.myTreesComments){
        opinions=JSON.parse(localStorage.myTreesComments);
    }

    opinions.push(newOpinion);
    localStorage.myTreesComments = JSON.stringify(opinions);
//localStorage.clear();

    window.location.hash="#opinions";

}
