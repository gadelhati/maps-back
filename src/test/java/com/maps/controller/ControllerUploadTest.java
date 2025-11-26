package com.maps.controller;

import com.maps.service.ServiceStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para ControllerUpload
 * Focando nos endpoints de upload e download de arquivos
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ControllerUploadTest {

    @Mock
    private ServiceStorage serviceStorage;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ControllerUpload controllerUpload;

    @Test
    void testListUploadedFiles_WithFiles_ShouldReturnModelAndView() throws IOException {
        // Arrange
        Path path1 = Paths.get("file1.txt");
        Path path2 = Paths.get("file2.txt");
        Stream<Path> pathStream = Stream.of(path1, path2);
        
        when(serviceStorage.loadAll()).thenReturn(pathStream);

        // Act
        ModelAndView result = controllerUpload.listUploadedFiles(model);

        // Assert
        assertNotNull(result);
        assertEquals("upload", result.getViewName());
        assertEquals("Upload", result.getModel().get("title"));
        verify(serviceStorage).loadAll();
        verify(model).addAttribute(eq("files"), anyList());
    }

    @Test
    void testListUploadedFiles_ServiceThrowsIOException_ShouldPropagateException() throws IOException {
        // Arrange
        when(serviceStorage.loadAll()).thenThrow(new IOException("Storage error"));

        // Act & Assert
        assertThrows(IOException.class, () -> {
            controllerUpload.listUploadedFiles(model);
        });

        verify(serviceStorage).loadAll();
    }

    @Test
    void testServeFile_WithExistingFile_ShouldReturnFileResponse() {
        // Arrange
        String filename = "test.txt";
        Resource mockResource = mock(Resource.class);
        
        when(mockResource.getFilename()).thenReturn(filename);
        when(serviceStorage.loadAsResource(filename)).thenReturn(mockResource);

        // Act
        ResponseEntity<Resource> response = controllerUpload.serveFile(filename);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResource, response.getBody());
        assertTrue(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains("attachment"));
        assertTrue(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains(filename));
        verify(serviceStorage).loadAsResource(filename);
    }

    @Test
    void testServeFile_WithNonExistentFile_ShouldReturnNotFound() {
        // Arrange
        String filename = "nonexistent.txt";
        
        when(serviceStorage.loadAsResource(filename)).thenReturn(null);

        // Act
        ResponseEntity<Resource> response = controllerUpload.serveFile(filename);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(serviceStorage).loadAsResource(filename);
    }

    @Test
    void testServeFile_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        String filename = "error.txt";
        
        when(serviceStorage.loadAsResource(filename))
            .thenThrow(new RuntimeException("File access error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerUpload.serveFile(filename);
        });

        verify(serviceStorage).loadAsResource(filename);
    }

    @Test
    void testHandleFileUpload_WithValidFiles_ShouldProcessFilesAndRedirect() {
        // Arrange
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        MultipartFile[] files = {file1, file2};
        
        when(file1.isEmpty()).thenReturn(false);
        when(file2.isEmpty()).thenReturn(false);
        doNothing().when(serviceStorage).store(any(MultipartFile.class));

        // Act
        String result = controllerUpload.handleFileUpload(files, redirectAttributes);

        // Assert
        assertEquals("redirect:/", result);
        verify(serviceStorage).store(file1);
        verify(serviceStorage).store(file2);
        verify(redirectAttributes).addFlashAttribute("message", "You successfully uploaded 2 files(s)!");
    }

    @Test
    void testHandleFileUpload_WithEmptyFiles_ShouldSkipEmptyFilesAndRedirect() {
        // Arrange
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        MultipartFile[] files = {file1, file2};
        
        when(file1.isEmpty()).thenReturn(true);
        when(file2.isEmpty()).thenReturn(false);
        doNothing().when(serviceStorage).store(file2);

        // Act
        String result = controllerUpload.handleFileUpload(files, redirectAttributes);

        // Assert
        assertEquals("redirect:/", result);
        verify(serviceStorage, never()).store(file1);
        verify(serviceStorage).store(file2);
        verify(redirectAttributes).addFlashAttribute("message", "You successfully uploaded 2 files(s)!");
    }

    @Test
    void testHandleFileUpload_WithAllEmptyFiles_ShouldRedirectWithoutProcessing() {
        // Arrange
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        MultipartFile[] files = {file1, file2};
        
        when(file1.isEmpty()).thenReturn(true);
        when(file2.isEmpty()).thenReturn(true);

        // Act
        String result = controllerUpload.handleFileUpload(files, redirectAttributes);

        // Assert
        assertEquals("redirect:/", result);
        verify(serviceStorage, never()).store(any(MultipartFile.class));
        verify(redirectAttributes).addFlashAttribute("message", "You successfully uploaded 2 files(s)!");
    }

    @Test
    void testHandleFileUpload_WithNoFiles_ShouldRedirect() {
        // Arrange
        MultipartFile[] files = {};

        // Act
        String result = controllerUpload.handleFileUpload(files, redirectAttributes);

        // Assert
        assertEquals("redirect:/", result);
        verify(serviceStorage, never()).store(any(MultipartFile.class));
        verify(redirectAttributes).addFlashAttribute("message", "You successfully uploaded 0 files(s)!");
    }

    @Test
    void testHandleFileUpload_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        MultipartFile[] files = {file};
        
        when(file.isEmpty()).thenReturn(false);
        doThrow(new RuntimeException("Storage error")).when(serviceStorage).store(file);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            controllerUpload.handleFileUpload(files, redirectAttributes);
        });

        verify(serviceStorage).store(file);
    }

    @Test
    void testConstructor_ShouldInitializeCorrectly() {
        // Arrange
        ServiceStorage mockService = mock(ServiceStorage.class);

        // Act
        ControllerUpload controller = new ControllerUpload(mockService);

        // Assert
        assertNotNull(controller);
    }

    @Test
    void testServeFile_WithFilenameThatHasExtension_ShouldHandleCorrectly() {
        // Test específico para filename com extensão (pathVariable pattern)
        String filename = "document.pdf";
        Resource mockResource = mock(Resource.class);
        
        when(mockResource.getFilename()).thenReturn(filename);
        when(serviceStorage.loadAsResource(filename)).thenReturn(mockResource);

        ResponseEntity<Resource> response = controllerUpload.serveFile(filename);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains("document.pdf"));
        verify(serviceStorage).loadAsResource(filename);
    }
}