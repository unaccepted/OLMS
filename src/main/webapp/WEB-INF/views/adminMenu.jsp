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
                   <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-folder-open"></span> Book Category<b class="caret"></b></a>
                   <ul class="dropdown-menu multi-level">
                       <li><a href="/olmsb-web/admin/bookcategory/entry/page">Entry</a></li>
                       <li><a href="/olmsb-web/admin/bookcategory/query/page">Query</a></li>
                   </ul>
               </li>
               <li>
                   <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-book"></span> Book<b class="caret"></b></a>
                   <ul class="dropdown-menu">
                       <li><a href="/olmsb-web/admin/book/entry/page">Entry</a></li>
                       <li><a href="/olmsb-web/admin/book/query/page">Query</a></li>
                   </ul>
               </li>
               <li>
                   <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-list-alt"></span> Issue<b class="caret"></b></a>
                   <ul class="dropdown-menu">
                       <li><a href="/olmsb-web/admin/issue/query/page">Query</a></li>
                   </ul>
               </li>
               <li>
                   <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user">Users<b class="caret"></b></a>
                   <ul class="dropdown-menu">
                       <li><a href="/olmsb-web/admin/users/existingUserQuery/page">Existing User Query</a></li>
                       <li><a href="/olmsb-web/admin/users/newRegistrationQuery/page">New Registrations Query</a></li>
                   </ul>
               </li>
           </ul>
       </div><!--/.nav-collapse -->
   </div>
</div>