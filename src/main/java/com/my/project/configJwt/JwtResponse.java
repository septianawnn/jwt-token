package com.my.project.configJwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author pi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {
    // class ini merupakan model yang digunakan untuk mengemas token JWT yang dihasilkan ke dalam respons HTTP
    private String token;
    private String tokenType;

    private Map<String, Object> userInfo;


    // HUBUNGAN ANTAR CLASS CONFIG JWT
    //WebSecurityConfig : mengatur konfigurasi keamanan dan menggunakan JwtRequestFilter untuk memproses token JWT pada setiap permintaan.
    //JwtRequestFilter : menggunakan JwtTokenService untuk mengambil informasi dari token dan memvalidasi token tersebut.
    //JwtAuthenticationEntryPoint : digunakan untuk menangani kesalahan otentikasi, memastikan bahwa jika pengguna tidak memiliki akses yang sah, respons yang sesuai dikirimkan.
    //JwtTokenService : bertanggung jawab untuk semua manipulasi token, termasuk pembuatan dan validasi.
    //JwtResponse : digunakan untuk mengembalikan token yang dihasilkan sebagai bagian dari respons otentikasi.

    // dengan adanya konfigurasi ini, sistem akan mengelola otentikasi pengguna menggunakan
    // token JWT, memastikan setiap permintaan diotentikasi sebelum mengakses sumber daya yang dilindungi.
}
