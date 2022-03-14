package com.avs.portal.trial;

import java.util.concurrent.CompletableFuture;

public class AsyncTrial {

	public static void main(String[] args) {
		new AsyncTrial()
			.tryCompletableFuture();
	}
	
	public void tryCompletableFuture() {
		CompletableFuture.supplyAsync(() -> 100)
			.thenApplyAsync(n -> {
				while(n > 1) {
					System.out.print(".");
					--n;
				}
				return n;
			})
			.thenAcceptAsync(n -> System.out.println(n))
			.thenRunAsync(() -> System.out.println("done."));
	}

}
