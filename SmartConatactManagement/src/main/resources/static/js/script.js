const toogleSidebar = () => {
    if($('.sidebar').is(":visible")){
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%")
    }
    else{
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%")
    }
};


//Searchbar implementation.

const search=()=>{

  //  console.log("searching");
  let query=$("#search-input").val();
  console.log(query);
  if(query=='')
  {
    $(".search-result").hide();
  }
  else{
     //search
     //console.log(query);

     //sending request to backend server 
    let url=`http://localhost:8282/search/${query}`;
    fetch(url)
           .then((response)=>{ 
        return response.json();
    })
      .then((data)=>{
      //data access
     // console.log(data);
      let text=`<div class='list-group'>`

      data.forEach(contactlist => {
          text+= `<a href='/user/${contactlist.contact_id}/contact' class='list-group-item list-group-item-action'>${contactlist.work} </a>`
      });

      text+=`</div>`
      
      $(".search-result").html(text);
      $(".search-result").show();
    });
     
  }
};