'use strict';

var Zap = {
		
/**
 * Subscrib to jahia app
 */
  pre_subscribe: function(bundle) {
	
	bundle.request.method = 'POST';
	// We only support json
	bundle.request.headers['Content-Type'] = 'application/json';
	
	var requestData = JSON.parse(bundle.request.data);
	requestData.owner = bundle.auth_fields.username;
	requestData.max_retry = '2';
	//bundle.request.content = requestData;
	return {
	    url: bundle.request.url,
	    method: bundle.request.method,
	    auth: bundle.request.auth,
	    headers: bundle.request.headers,
	    params: bundle.trigger_fields,
	    data: JSON.stringify(requestData)
	    };
  },
  
  post_subscribe: function(bundle) {
	  var dataResponse = JSON.parse(bundle.response.content);
      
	  if(dataResponse && dataResponse.id !== '') {
		  return {webhook_id: dataResponse.id};
	  } else {
		  return {};
	  }
  },
  
  /**
   * Unsubscrib to jahia app
   */
  pre_unsubscribe: function(bundle) {
	    
		  bundle.request.method = 'DELETE';
	      // bundle.subscribe_data is from return date in post_subscribe method
	      //bundle.request.url = bundle.request.url + 'cms/hooks?id=' + bundle.subscribe_data.webhook_id;
	      
      //return bundle.request;
	    return {
	      url: bundle.request.url,
	      method: 'DELETE',
	      auth: bundle.request.auth,
	      headers: bundle.request.headers,
	      //params: bundle.request.params.push('id'),
	      data: bundle.request.data
	    }; // or return bundle.request;
	  },
	  
	  // Hook notification
	  task_created_pre_hook: function(bundle) {
		    
		    return {
		      url: bundle.request.url + 'cms/data',
		      method: bundle.request.method,
		      auth: bundle.request.auth,
		      headers: bundle.request.headers,
		      params: bundle.request.params,
		      data: bundle.request.data
		    }; // or return bundle.request;
		  },
		  
		  // post notification
		  task_created_post_hook: function(bundle) {
			    
                // transform data or let as it comes?
			    return [bundle.response.content];
			  },
		  
		// test ping
		  test_pre_hook: function(bundle) {
			    
			    return {
			      url: bundle.request.url + 'cms/ping',
			      method: bundle.request.method,
			      auth: bundle.request.auth,
			      headers: bundle.request.headers,
			      params: bundle.request.params,
			      data: bundle.request.data
			    }; // or return bundle.request;
			  },
			  
			  // post test
			  test_post_hook: function(bundle) {
				    
				    return [{
				    	message: bundle.response.content.message,
				    	dateTime: bundle.response.content.dateTime
				    }];
				  }
  
};