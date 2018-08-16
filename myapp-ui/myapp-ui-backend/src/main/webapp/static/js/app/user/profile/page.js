var imagePath;
var url = window.location.href;
var cropBoxData;
var canvasData;
var $image;

function init() {

  if ($('#contentId').val() == "" || $('#contentId').val() == undefined) {
    $("#profile-img").attr("src", getStaticResourcePath() + "/images/avatar/guest.jpg");
  } else {
    $('#profile-img').attr('src', getContextPath() + "/files/" + $('#contentId').val());
  }
  $('.passwordFormatTooltip').tooltipster({
    animation: 'fade',
    delay: 200,
    maxWidth: 300,
    trigger: 'hover',
    content: 'Password must be minimum of 8 characters with (at least 1 uppercase alphabet, 1 lowercase alphabet, 1 number and 1 special character) but should not start with special characters.'
  });
}

function bind() {
  validate();
  /* Handle image resized events */
  $(document).on("imageResized", function(event) {
    if (event.url) {
      var image = new Image();
      $("#imageHolder").attr("src", event.url);
      $(".cropArea,.docs-toolbar").show();
      initCropper();
    }
  });
  $(".docs-toolbar").on("click", "[data-method]", function() {
    var data = $(this).data();

    if (data.method) {
      $image.cropper(data.method, data.option);
    }
  });
  $(".docs-toolbar .download").click(function() {
    if ($image) {
      var croppedCanvas = $image.cropper('getCroppedCanvas');
      window.open(croppedCanvas.toDataURL());
    }
  });

  $('#profileImageCropper').on('shown.bs.modal', function() {
    $("#selectedImage").addClass("img-circle");
    $(".cropArea,.docs-toolbar").hide();
    $("#selectedImage").removeAttr("style");
    $("#selectedImage").attr("src", getStaticResourcePath() + "/images/avatar/guest.jpg");
  }).on('hidden.bs.modal', function() {
    $("#btnUploadImage").removeClass("disabled").removeAttr("disabled");
    if ($image) {
      cropBoxData = $image.cropper('getCropBoxData');
      canvasData = $image.cropper('getCanvasData');
      $image.cropper('destroy');
      $image = undefined;
    }
  });

  $('#editImage').on('click', function(e) {
    $('#profileImageCropper').modal('show');
  });
  $("#btnSetGravatorImage").on('click', function(e) {
    if ($image) {
      $("#selectedImage").attr("src", getStaticResourcePath() + "/images/avatar/guest.jpg");
      $image.cropper('destroy');
      $image = undefined;
      $(".cropArea,.docs-toolbar").hide();
      $("#selectedImage").removeAttr("style");
      $("#selectedImage").addClass("img-circle");
    }
    var count = 0;
    var email = new Date().getTime() + "@gmail.com";
    // http://en.gravatar.com/site/implement/images/
    imagePath = 'http://www.gravatar.com/avatar/' + md5(email);

    var gravatarUrl = imagePath + "?s=150&d=identicon&r=g&f=1";
    $('#selectedImage').attr('src', gravatarUrl);
    $('#selectedImage').error(function() {
      alert("Can't retrieve avator image.Check your internet connection.");
      $("#selectedImage").attr("src", getStaticResourcePath() + "/images/avatar/guest.jpg");
    });
  });
  $("#btnConfirmImage").on('click', function(e) {
    if ($image) {
      var croppedCanvas = $image.cropper('getCroppedCanvas');
      $("#profile-img").attr("src", croppedCanvas.toDataURL());
    } else {
      $("#profile-img").attr("src", $('#selectedImage').attr('src'));
    }
    $('#profileImageCropper').modal('hide');
  });
  $("#profilePhotoFileInput").change(function(event) {
    var file = event.target.files[0];
    // Ensure it's an image
    if (file.type.match(/image.*/)) {
      // Load the image
      if (typeof (FileReader) != "undefined") {
        var reader = new FileReader();
        reader.onload = function(readerEvent) {
          var image = new Image();
          image.onload = function(imageEvent) {
            // Resize the image
            var canvas = document.createElement('canvas'), max_size = 544, width = image.width, height = image.height;
            if (width > height) {
              if (width > max_size) {
                height *= max_size / width;
                width = max_size;
              }
            } else {
              if (height > max_size) {
                width *= max_size / height;
                height = max_size;
              }
            }
            canvas.width = width;
            canvas.height = height;
            canvas.getContext('2d').drawImage(image, 0, 0, width, height);
            var dataUrl = canvas.toDataURL('image/png');
            $.event.trigger({
              type: "imageResized",
              url: dataUrl
            });
          }
          image.src = readerEvent.target.result;
        }
        reader.readAsDataURL(file);
      } else {
        alert("Your browser seems very old. Please update your browser version to support.");
      }
    } else {
      alert("Please select only Images.");
    }
    $("#btnUploadImage").addClass("disabled").attr("disabled", "true");
  });
  $("#btnUploadImage").on('click', function(e) {
    $("#profilePhotoFileInput").trigger("click");
  });
  $("#btnSave").on("click", function(e) {
    e.preventDefault();
    if ($('#profile-img').attr('src') == getContextPath() + "/files/" + $('#contentId').val()) {
      $("#userForm").submit();
    } else {
      uploadProfilePicture($("#profile-img").attr("src"));
    }
  });

  // Updating Password
  $("#confirmChangePassword").on('click', function(e) {
    pwdValidate();
    if ($("#reset_PasswordForm").valid()) {
      $.ajax({
        url: getContextPath() + "/user/changedPassword",
        type: "POST",
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        data: {
          'oldPassword': $("#change_oldPassword").val(),
          'newPassword': $("#change_newPassword").val(),
          'confirmPassword': $("#change_confirmPassword").val(),
        },
        success: function(response) {
          handleServerResponse(response);
        },
      });
    }
  });

}
function initCropper() {
  $image = $('#imageHolder');
  var $button = $('#uploadProfilePhoto');
  var $previews = $('.preview');
  $image.cropper({
    aspectRatio: 1,
    viewMode: 1,
    autoCropArea: 0.5,
    ready: function(e) {
      $image.cropper('setCanvasData', canvasData);
      $image.cropper('setCropBoxData', cropBoxData);
      var $clone = $(this).clone().removeClass('cropper-hidden').addClass("img-responsive img-thumbnail ").attr("id", "selectedImage");
      $clone.css({
        display: 'block',
        width: '100%',
        minWidth: 0,
        minHeight: 0,
        maxWidth: 'none',
        maxHeight: 'none'
      });

      $previews.html($clone);
    },

    crop: function(e) {
      var imageData = $(this).cropper('getImageData');
      var previewAspectRatio = e.width / e.height;

      $previews.each(function() {
        var $preview = $(this);
        var previewWidth = $preview.width();
        var previewHeight = previewWidth / previewAspectRatio;
        var imageScaledRatio = e.width / previewWidth;

        $preview.height(previewHeight).find('img').css({
          width: imageData.naturalWidth / imageScaledRatio,
          height: imageData.naturalHeight / imageScaledRatio,
          marginLeft: -e.x / imageScaledRatio,
          marginTop: -e.y / imageScaledRatio
        });
      });
    }
  });
}
function uploadProfilePicture(imageUrl) {
  if (imageUrl) {
    if (imageUrl.indexOf("http://www.gravatar.com/avatar") > -1 || imageUrl.indexOf("/images/avatar/guest.jpg") > -1 || imageUrl.indexOf("/common/images/no-image.png") > -1) {
      var img = new Image();
      img.setAttribute('crossOrigin', 'anonymous');
      img.onload = function() {
        var canvas = document.createElement("canvas");
        canvas.width = this.width;
        canvas.height = this.height;
        var ctx = canvas.getContext("2d");
        ctx.drawImage(this, 0, 0);
        var dataURL = canvas.toDataURL("image/png");
        uploadImage(dataURL);
      };
      img.src = imageUrl;

    } else {
      uploadImage(imageUrl);
    }
  }
}

function uploadImage(imageData) {
  var filename = "profile_logo.png";
  var fullPath = $("#profilePhotoFileInput").val();
  if (fullPath) {
    var startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
    filename = fullPath.substring(startIndex);
    if (filename.indexOf('\\') === 0 || filename.indexOf('/') === 0) {
      filename = filename.substring(1);
    }
  }
  var data = new FormData();
  var blob = dataURItoBlob(imageData);
  data.append("imageFile", blob, filename);
  data.append("type", "profilePicture");
  jQuery.ajax({
    url: getContextPath() + '/ajax/upload',
    data: data,
    cache: false,
    contentType: false,
    processData: false,
    type: 'POST',
    success: function(data) {
      console.log(data);
      if (data.status == "OK") {
        $.each(data.uploadFiles, function(index, fileItem) {
          $('#contentId').val(fileItem.contentId);
          $("#userForm").submit();
        });
      } else {
        alert(data.result.errorMessage);
      }
    }
  });
}

function dataURItoBlob(dataURI) {
  // convert base64 to raw binary data held in a string
  // doesn't handle URLEncoded DataURIs - see SO answer #6850276 for code that
  // does this
  var byteString = atob(dataURI.split(',')[1]);

  // separate out the mime component
  var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

  // write the bytes of the string to an ArrayBuffer
  var ab = new ArrayBuffer(byteString.length);
  var ia = new Uint8Array(ab);
  for (var i = 0; i < byteString.length; i++) {
    ia[i] = byteString.charCodeAt(i);
  }

  // write the ArrayBuffer to a blob, and you're done
  var blob = new Blob([ab], {
    type: mimeString
  });
  return blob;
}

function pwdValidate() {
  $("#reset_PasswordForm").validate({
    rules: {
      change_oldPassword: {
        required: true
      },
      change_newPassword: {
        required: true
      },
      change_confirmPassword: {
        required: true,
        equalTo: "#change_newPassword"
      }

    },
    messages: {
      change_oldPassword: {
        required: "'Old Password' should not be empty."
      },
      change_newPassword: {
        required: "'New Password' should not be empty."
      },
      change_confirmPassword: {
        required: "'Confirm New Password' should not be empty.",
        equalTo: "'New Password' and 'Confirm New Password' do not match."
      }
    }

  });
}

function validate() {
  $("#userForm").validate({
    rules: {
      name: {
        required: true,
        maxlength: 50,
      }
    },
    messages: {
      name: {
        required: "'Name' should not be empty.",
        maxlength: "'Name' should not exceeds 50 characters.",
      }
    }
  })
}