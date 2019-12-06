package com.swarm.zuul;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class DefaultPreFilter extends ZuulFilter {
	
	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return !ctx.containsKey(FilterConstants.FORWARD_TO_KEY) // a filter has already forwarded
				&& !ctx.containsKey(FilterConstants.SERVICE_ID_KEY); // a filter has already determined serviceId
	}

	@Override
	public Object run() throws ZuulException {
		//TODO request parameters handle
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;
	}

}
