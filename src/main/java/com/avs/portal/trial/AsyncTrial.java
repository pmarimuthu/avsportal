package com.avs.portal.trial;

import java.util.concurrent.CompletableFuture;

import com.avs.portal.enums.LogStatusEnum;
import com.avs.portal.util.Logger;

public class AsyncTrial {

	public static void main(String[] args) {
		new AsyncTrial()
			.tryCompletableFuture();
	}
	
	public void tryCompletableFuture() {
		CompletableFuture.supplyAsync(() -> 100)
			.thenApplyAsync(n -> {
				while(n > 1) {
					--n;
				}
				return n;
			})
			.thenAcceptAsync(n -> Logger.log(LogStatusEnum.INFO, "getUsersByIdOrEmailAndPhone", String.valueOf(n) ))
			.thenRunAsync(() -> Logger.log(LogStatusEnum.INFO, "getUsersByIdOrEmailAndPhone", "done."));
	}

}
