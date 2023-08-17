export default class articleFormsHandler {

    constructor(articlesServerUrl) {
        this.serverUrl=articlesServerUrl;
    }

    assignFormAndArticle(formElementId, cssClass2hideElement){
        this.cssCl2hideElm  = cssClass2hideElement;
        const artForm=document.getElementById(formElementId);
        this.formElements=artForm.elements;

        this.formElements.namedItem('btFileUpload2').onclick= ()=>this.uploadImg();
        this.formElements.namedItem('btCancelFileUpload2').onclick= ()=>this.cancelFileUpload();

        artForm.onsubmit= (event)=>this.processArtEditFrmData(event);
    }

    /**
     *Pridanie funkcionality pre kliknutie na tlacidlo "Nahraj obrázok / Upload image"
     * Adding functionality for the button "Nahraj obrázok / Upload image"
     */
    showFileUpload(event) {
        this.formElements.namedItem('fsetFileUpload2').classList.remove( this.cssCl2hideElm);
        this.formElements.namedItem('btShowFileUpload2').classList.add( this.cssCl2hideElm);
    }

    /**
     *Pridanie funkcionality pre kliknutie na tlacidlo "Zruš nahrávanie / Cancel uploading"
     *Adding functionality for the button "Zruš nahrávanie / Cancel uploading"
     */
    cancelFileUpload() {
        this.formElements.namedItem('fsetFileUpload2').classList.add( this.cssCl2hideElm);
        this.formElements.namedItem('btShowFileUpload2').classList.remove( this.cssCl2hideElm);
    }

    /**
     * uploads a new image to the server , closes the corresponding part of the form and adds the url of the image to the form.
     */
    uploadImg() {

        const files = this.formElements.namedItem("flElm2").files;

        if (files.length > 0) {
            const imgLinkElement = this.formElements.namedItem("imageLink2");
            const fieldsetElement = this.formElements.namedItem("fsetFileUpload2");
            const btShowFileUploadElement = this.formElements.namedItem("btShowFileUpload2");

            //1. Gather  the image file data

            let imgData = new FormData();     //obrazok su binarne udaje, preto FormData (pouzitelne aj pri upload-e viac suborov naraz)
                                              //and image is binary data, that's why we use FormData (it works for multiple file upload, too)
            imgData.append("file", files[0]); //beriem len prvy obrazok, ved prvok formulara by mal povolit len jeden
                                              //takes only the first file (image)

            //2. Set up the request


            const postReqSettings = //an object wih settings of the request
                {
                    method: 'POST',
                    body: imgData //FormData object, not JSON this time.
                };


            //3. Execute the request

            fetch(`${this.serverUrl}/fileUpload`, postReqSettings)  //now we need the second parameter, an object wih settings of the request.
                .then(response => {      //fetch promise fullfilled (operation completed successfully)
                    if (response.ok) {    //successful execution includes an error response from the server. So we have to check the return status of the response here.
                        return response.json(); //we return a new promise with the response data in JSON to be processed
                    } else { //if we get server error
                        return Promise.reject(new Error(`Server answered with ${response.status}: ${response.statusText}.`)); //we return a rejected promise to be catched later
                    }
                })
                .then(responseJSON => { //here we process the returned response data in JSON ...
                    imgLinkElement.value = responseJSON.fullFileUrl;
                    btShowFileUploadElement.classList.remove( this.cssCl2hideElm);
                    fieldsetElement.classList.add( this.cssCl2hideElm);
                })
                .catch(error => { ////here we process all the failed promises
                    window.alert(`Image uploading failed. ${error}.`);
                });
        } else {
            window.alert("Please, choose an image file.");
        }


    }

    /**
     * Process form data and sends the article to server
     * @param event - event object, to prevent default processing
     */
    processArtEditFrmData(event) {
        event.preventDefault();

        //1. Gather and check the form data
        const articleData = {
            title: this.formElements.namedItem("title2").value.trim(),
            content: this.formElements.namedItem("content2").value.trim(),
            author: this.formElements.namedItem("author2").value.trim(),

            imageLink: this.formElements.namedItem("imageLink2").value.trim(),
            tags: this.formElements.namedItem("tags2").value.trim()
        };

        if (!(articleData.title && articleData.content)) {
            window.alert("Please, enter article title and content2");
            return;
        }

        if (!articleData.author) {
            articleData.author = "Anonymous";
        }

        if (!articleData.imageLink) {
            delete articleData.imageLink;
        }

        if (!articleData.tags) {
            delete articleData.tags;
        } else {
            articleData.tags = articleData.tags.split(","); //zmeni retazec s tagmi na pole. Oddelovac poloziek je ciarka.
            //changes the string with tags to array. Comma is the separator
            articleData.tags = articleData.tags.map(tag => tag.trim()); //odstráni prázdne znaky na začiatku a konci každého kľúčového slova
            //deletes white spaces from the beginning and the end of each tag string

            //newArtData.tags=newArtData.tags.map(function(tag) {return tag.trim()}); //alternativny sposob zapisu predch. prikazu
            //an alternative way of writing the previous command

            articleData.tags = articleData.tags.filter(tag => tag); //odstráni tie tagy, ktoré sú teraz len prázdne reťazce
            //removes those tags that are now just empty strings
            if (articleData.tags.length == 0) {
                delete articleData.tags;
            }
        }

        //2. Set up the request


        const postReqSettings = //an object wih settings of the request
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8',
                },
                body: JSON.stringify(articleData)
            };


        //3. Execute the request


        fetch(`${this.serverUrl}/article`, postReqSettings)  //now we need the second parameter, an object wih settings of the request.
            .then(response => {      //fetch promise fullfilled (operation completed successfully)
                if (response.ok) {    //successful execution includes an error response from the server. So we have to check the return status of the response here.
                    return response.json(); //we return a new promise with the response data in JSON to be processed
                } else { //if we get server error
                    return Promise.reject(new Error(`Server answered with ${response.status}: ${response.statusText}.`)); //we return a rejected promise to be catched later
                }
            })
            .then(responseJSON => { //here we process the returned response data in JSON ...
                window.alert("Updated article successfully saved on server");
            })
            .catch(error => { ////here we process all the failed promises
                window.alert(`Failed to save the updated article on server. ${error}`);

            })
            //.finally(() => window.location.hash = `#article/${ this.articleId}/${ this.offset}/${ this.totalCount}`);
            .finally(() => window.location.hash = `#welcome`);

    }
}

