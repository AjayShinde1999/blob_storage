$(function () {
    const form = $(".new-blog-form");
    const attachmentValue = $(".attachment-input");
    const blogsContainer = $(".blogs-list");

    form.on('submit', function (e) {
        e.preventDefault();
        const formValue = form.serializeArray();
        const attachment = attachmentValue.prop("files")[0];
        const date = new Date();
        const payload = {
            title: formValue[0].value,
            description: formValue[1].value,
            imageUrl: attachment,
            date: `${(date.getDate() + 1).toString().padStart(2, '0')}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getFullYear()}`,
            time: `${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`
        };
        const newFormData = new FormData();
        newFormData.append('title', payload.title);
        newFormData.append('description', payload.description);
        newFormData.append('imageUrl', payload.imageUrl);
        newFormData.append('date', payload.date);
        newFormData.append('time', payload.time);

        sendBlogData(newFormData);
    })

    function sendBlogData(data) {
        $.ajax({
            type: 'POST',
            url: 'https://blobstorage-production.up.railway.app/api/admin',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            success: (res) => {
                form.trigger("reset");
                fetchAllData();
            },
            error: (err) => {
                console.log(err);
                form.trigger("reset");
            }
        });
    }

    function fetchAllData() {
        $.ajax({
            type: 'GET',
            url: 'https://blobstorage-production.up.railway.app/api/admin',
            dataType: 'json',
            success: (res) => {
                generateBlog(res);
            },
            error: (err) => {
                console.log(err);
            }
        })
    }

    fetchAllData();


    function generateBlog(data) {
        blogsContainer.html("");
        data.length && data.reverse().forEach((ele) => {
            const li = `
                        <li class="blog">
                            <div class="img-wrapper">
                                <img src=${ele.imageUrl} alt="blog-img">
                            </div>
                            <div class="time-stamp">
                                <span class="date">Created On : ${ele.date}</span>
                                <span class="time">Time : ${ele.time}</span>
                            </div>
                            <h3 class="title">${ele.title}</h3>
                            <p class="description">${ele.description}</p>
                        </li>`;
            blogsContainer.append(li);
        })
    }
})
