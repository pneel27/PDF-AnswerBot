package com.javaAI.PDFChat;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class PdfChatApplication {

		private final EmbeddingStoreIngestor embeddingStoreIngestor;

		public PdfChatApplication(EmbeddingStoreIngestor embeddingStoreIngestor) {
			this.embeddingStoreIngestor = embeddingStoreIngestor;
		}

		@PostConstruct
		public void init() {
			Document document = loadDocument(toPath("Medical_book.pdf"), new ApachePdfBoxDocumentParser());
			embeddingStoreIngestor.ingest(document);
		}


		private static Path toPath(String fileName) {
			try {
				URL fileUrl = PdfChatApplication.class.getClassLoader().getResource(fileName);
				return Paths.get(fileUrl.toURI());
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}



	public static void main(String[] args) {
		SpringApplication.run(PdfChatApplication.class, args);
	}

}
