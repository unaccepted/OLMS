<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
   <div class="menu-container">
       <div class="navbar-header">
           <a class="navbar-brand navbar-left" href="/olmsb-web/dashboard">OLMS</a>
       </div>
       <div class="collapse navbar-collapse">
           <ul class="nav navbar-nav navbar-right">
           	<li>
                   <a href="#" class="dropdown-toggle" data-toggle="dropdown">${userID} <b class="caret"></b></a>
                   <ul class="dropdown-menu">
                       <li><a href="/olmsb-web/logout">Logout</a></li>
                   </ul>
               </li>
           </ul>
           <ul class="nav navbar-nav navbar-left">
               <li>
                   <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-book"></span> Books<b class="caret"></b></a>
                   <ul class="dropdown-menu multi-level">
                       <li><a href="/olmsb-web/member/books/query/page">Issue Books</a></li>
                       <li><a href="/olmsb-web/member/books/return/page">Return Books</a>
                   </ul>
               </li>
               <li>
                   <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-list-alt"></span> Issue Status<b class="caret"></b></a>
                   <ul class="dropdown-menu">
                       
                       <li><a href="/olmsb-web/member/issue/query/page">Query</a></li>
                   </ul>
               </li>
           </ul>
       </div><!--/.nav-collapse -->
   </div>
</div>