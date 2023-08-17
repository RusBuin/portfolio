

import Mustache from "./mustache.js";
import processOpnFrmData from "./addOpinion.js";
import articleFormsHandler from "./articleFormsHandler.js";
import addArticle from "./addArticle.js";

export default [

    {
        hash: "welcome",
        target: "router-view",
        getTemplate: (targetElm) =>
            document.getElementById(targetElm).innerHTML = document.getElementById("template-welcome").innerHTML
    },
    {
        hash: "articles",
        target: "router-view",
        getTemplate: fetchAndDisplayArticles
    },
    {
        hash: "opinions",
        target: "router-view",
        getTemplate: createHtml4opinions
    },
    {
        hash: "addOpinion",
        target: "router-view",
        getTemplate: (targetElm) => {
            document.getElementById(targetElm).innerHTML = document.getElementById("template-addOpinion").innerHTML;
            document.getElementById("opnFrm").onsubmit = processOpnFrmData;
        }
    },
    {
        hash: "article",
        target: "router-view",
        getTemplate: fetchAndDisplayArticleDetail
    },
    {
        hash: "artEdit",
        target: "router-view",
        getTemplate: editArticle
    },
    {
        hash: "artDelete",
        target: "router-view",
        getTemplate: deleteArticle
    },
    {
        hash: "artInsert",
        target: "router-view",
        getTemplate: postArticle
    },
    {
        hash: "comments",
        target: "router-view",
        getTemplate: displayComments
    },
    {
        hash: "addComment",
        target: "router-view",
        getTemplate: postComment
    },
    {
        hash: "show",
        target: "router-view",
        getTemplate: openForm
    },
    {
        hash: "findArtbyTag",
        target: "router-view",
        getTemplate: fetchAndDisplayArticles
    }
];

const urlBase = "https://wt.kpi.fei.tuke.sk/api";

function createHtml4opinions(targetElm) {
    const opinionsFromStorage = localStorage.myTreesComments;
    let opinions = [];

    if (opinionsFromStorage) {
        opinions = JSON.parse(opinionsFromStorage);
        opinions.forEach(opinion => {
            opinion.created = (new Date(opinion.created)).toDateString();
            opinion.willReturn = opinion.willReturn ? "I will return to this page." : "Sorry, one visit was enough.";
        });
    }

    document.getElementById(targetElm).innerHTML = Mustache.render(
        document.getElementById("template-opinions").innerHTML,
        opinions
    );
}


let offsets = 0;

let beginPage = 0;
let total = 0;
var totalPage = 0;

function fetchAndDisplayArticles(targetElm, current, off, tags) {
    current = parseInt(current);
    let offs = parseInt(off);
    totalPage = 850 / 20;
    totalPage = Math.ceil(totalPage);
    const datas = {
        currPage: current,
        artCount: totalPage,
        off: offs
    };

    if (current > 1) {
        datas.prevPage = current - 1;
    }

    if (current < totalPage) {
        datas.nextPage = current + 1;
    }

    if (current > beginPage) {
        offsets += 20;
    }
    if(current===beginPage || current<beginPage){
        offsets -= 20;
    }

    if (current === 0) {
        offsets = 20;
    }
    beginPage = current;


    datas.off = offsets;
    let url = `${urlBase}/article?offset=${offsets}&max=20`;
    var tagInfo = document.getElementById("tagName")

    const datasTag = {
        tagInfo: tagInfo?.value || null
    };

    if (datasTag.tagInfo !== null) {
        url += `&tag=${datasTag.tagInfo}`;
    }
    function reqListener() {
        console.log(this.responseText)
        if (this.status == 200) {
            const responseJSON = JSON.parse(this.responseText)
            total = responseJSON.meta.totalCount;

            responseJSON.data = datas;
            addArtDetailLink2ResponseJson(responseJSON);
            document.getElementById(targetElm).innerHTML =
                Mustache.render(
                    document.getElementById("template-articles").innerHTML, responseJSON
                );

        } else {
            const errMsgObj = {errMessage: this.responseText};
            document.getElementById(targetElm).innerHTML =
                Mustache.render(
                    document.getElementById("template-articles-error").innerHTML, errMsgObj
                );
        }

    }




    console.log(url)
    var ajax = new XMLHttpRequest();
    ajax.addEventListener("load", reqListener);
    ajax.open("GET", url, true);
    ajax.send();
}

function addArtDetailLink2ResponseJson(responseJSON) {
    responseJSON.articles = responseJSON.articles.map(
        article => (
            {
                ...article,
                detailLink: `#article/${article.id}/${responseJSON.meta.offset}/${responseJSON.meta.totalCount}`
            }
        )
    );
}

function editArticle(targetElm, artIdFromHash, offsetFromHash, totalCountFromHash) {
    fetchAndProcessArticle(...arguments, true);
}

function postArticle(targetElm, artIdFromHash, offsetFromHash, totalCountFromHash) {
    document.getElementById(targetElm).innerHTML = document.getElementById("template-addArticle").innerHTML

    if (!window.artadd) {
        window.artadd = new addArticle("https://wt.kpi.fei.tuke.sk/api");
    }
    window.artadd.assignFormAndArticle("articleForm2", "hiddenElm2");
}

function deleteArticle(targetElm, artIdFromHash, offsetFromHash, totalCountFromHash) {
    fetch(`${urlBase}/article/${artIdFromHash}`, {method: 'DELETE'})
        .then(window.alert("The post was successfully deleted"))
        .finally(() => window.location.hash = `#articles/${offsetFromHash}/${totalCountFromHash}`);
}

function fetchAndDisplayArticleDetail(targetElm, artIdFromHash, offsetFromHash, totalCountFromHash) {
    fetchAndProcessArticle(...arguments, false);

}

let offsetForComment = 0;
function fetchAndProcessArticle(targetElm,artIdFromHash,offsetFromHash,totalCountFromHash,forEdit){
    const url = `${urlBase}/article/${artIdFromHash}`;

    function reqListener () {
        // stiahnuty text
        console.log(this.responseText)
        if (this.status == 200) {
            const responseJSON = JSON.parse(this.responseText)
            if(forEdit){
                responseJSON.formTitle="Article Edit";
                responseJSON.submitBtTitle="Save article";
                responseJSON.backLink=`#article/${artIdFromHash}/${offsetFromHash}/${totalCountFromHash}`;

                document.getElementById(targetElm).innerHTML =
                    Mustache.render(
                        document.getElementById("template-article-form").innerHTML,
                        responseJSON
                    );
                if(!window.artFrmHandler){
                    window.artFrmHandler= new articleFormsHandler("https://wt.kpi.fei.tuke.sk/api");
                }
                window.artFrmHandler.assignFormAndArticle("articleForm","hiddenElm",artIdFromHash,offsetFromHash,totalCountFromHash);
            }else{
                responseJSON.backLink=`#articles/${offsetFromHash}/${totalCountFromHash}`;
                responseJSON.editLink=
                    `#artEdit/${responseJSON.id}/${offsetFromHash}/${totalCountFromHash}`;
                responseJSON.deleteLink=
                    `#artDelete/${responseJSON.id}/${offsetFromHash}/${totalCountFromHash}`;
                displayComments(responseJSON.id, offsetForComment);
                responseJSON.JSONComments=responseJSONComment;
                responseJSON.addComm = `#addComment/${responseJSON.id}/${totalCountFromHash}`;
                document.getElementById(targetElm).innerHTML =
                    Mustache.render(
                        document.getElementById("template-article").innerHTML,
                        responseJSON
                    );
                const incrementCount = document.getElementById("increment-count-comm");
                const decrementCount = document.getElementById("decrement-count-comm");
                console.log(offsetForComment);
                incrementCount.addEventListener("click",()=>{
                    if(offsetForComment != 0){
                        offsetForComment--;
                        fetchAndProcessArticle(targetElm,artIdFromHash,offsetFromHash,totalCountFromHash,forEdit);
                    }
                });
                decrementCount.addEventListener("click",()=>{
                    offsetForComment++;
                    fetchAndProcessArticle(targetElm,artIdFromHash,offsetFromHash,totalCountFromHash,forEdit);
                });
            }
        } else {
            const errMsgObj = {errMessage:this.responseText};
            document.getElementById(targetElm).innerHTML =
                Mustache.render(
                    document.getElementById("template-articles-error").innerHTML,
                    errMsgObj
                );
        }

    }

    console.log(url)
    var ajax = new XMLHttpRequest();
    ajax.addEventListener("load", reqListener);
    ajax.open("GET", url, true);
    ajax.send();
}

let responseJSONComment = null;

function displayComments(id, comOffsets) {

    const url = `${urlBase}/article/${id}/comment/?max=10&offset=${comOffsets}`;

    function comm() {
        console.log("comments");
        console.log(this.responseText)
        if (this.status === 200) {
            responseJSONComment = JSON.parse(this.responseText)
        } else {
            alert("Error responseJSONComment");
        }
    }

    var ajax = new XMLHttpRequest();
    ajax.addEventListener("load", comm);
    ajax.open("GET", url, false);
    ajax.send();
}



function openForm() {
    const formWrap = document.getElementById('form-wrap');
    formWrap.classList.add('open');
}



function postComment(targetElm, artId, offsetFromHash) {

    const commentsData =
        {
            author: document.getElementById("author3").value.trim(),
            text: document.getElementById("title3").value.trim()
        };
    if (commentsData.text === "" || commentsData.author === "") {
        window.alert("Please, enter both your name and comment");
        return;
    }
    const postReqSettings =
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
            },
            body: JSON.stringify(commentsData)
        };
    fetch(`${urlBase}/article/${artId}/comment`, postReqSettings)
        .then(() => window.location.hash = `#article/${artId}/${offsetFromHash}`);
}






